#!/bin/bash
HOME=/home/swoehrl/projects/wis
HOST=localhost
USER=wis
PASSWORD=wis
DB=wis

echo Creating tables
cat schema.sql | psql -h $HOST -U $USER -W
echo Loading metadata
python bulkload_creator.py
psql -h $HOST -U $USER -W << EOF
TRUNCATE TABLE wis_partei, wis_bundesland, wis_bundestagswahl, wis_kandidat, wis_landesliste CASCADE;
copy wis_partei (id, name, displaycolor) from '$HOME/import_wis_partei.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_bundesland (id, name) from '$HOME/import_wis_bundesland.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_wahlkreis (id, name, ort, bundesland, anzahlwahlberechtigte) from '$HOME/import_wis_wahlkreis.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_wahlbezirk (id, name, ort, sitzwahllokal, wahlkreis) from '$HOME/import_wis_wahlbezirk.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_bundestagswahl (id, jahr) from '$HOME/import_wis_bundestagswahl.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_kandidat (id, vorname, nachname, partei) from '$HOME/import_wis_kandidat.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_landesliste (id, name, partei, wahl, bundesland) from '$HOME/import_wis_landesliste.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_kandidatur (id, wahl, kandidat, landesliste, listenplatz, wahlkreis, partei) from '$HOME/import_wis_kandidatur.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
EOF
echo Generating votes
java -jar voteprocessor.jar 1 wahldaten2009.csv firstvotes2009.csv 2009 true
java -jar voteprocessor.jar 2 wahldaten2009.csv secondvotes2009.csv 2009 true
java -jar viteprocessor.jar 3 wahldaten2005.csv firstvotes2005.csv 2005 false 
java -jar voteprocessor.jar 4 wahldaten2005.csv secondvotes2005.csv 2005 false 

echo Loading votes
psql -h $HOST -U $USER -W << EOF
ALTER TABLE wis_erststimme DROP CONSTRAINT wis_erststimme_abgegebenin_fkey;
ALTER TABLE wis_erststimme DROP CONSTRAINT wis_erststimme_fuerkandidat_fkey;
ALTER TABLE wis_zweitstimme DROP CONSTRAINT wis_zweitstimme_abgegebenin_fkey;
ALTER TABLE wis_zweitstimme DROP CONSTRAINT wis_zweitstimme_fuerliste_fkey;
copy wis_zweitstimme (fuerliste, abgegebenin) from '$HOME/secondvotes2009.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_erststimme (fuerkandidat, abgegebenin) from '$HOME/firstvotes2009.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_erststimmenergebnis (fuerkandidatur, inwahlkreis, anzahlstimmen) from '$HOME/firstvotes2005.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
copy wis_zweitstimmenergebnis (fuerliste, inwahlkreis, anzahlstimmen) from '$HOME/secondvotes2005.csv' WITH DELIMITER AS ';' NULL AS '' CSV;
ALTER TABLE wis_zweitstimme ADD   CONSTRAINT wis_zweitstimme_abgegebenin_fkey FOREIGN KEY (abgegebenin) REFERENCES wis_wahlbezirk (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE wis_zweitstimme ADD CONSTRAINT wis_zweitstimme_fuerliste_fkey FOREIGN KEY (fuerliste) REFERENCES wis_landesliste (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE wis_erststimme ADD CONSTRAINT wis_erststimme_abgegebenin_fkey FOREIGN KEY (abgegebenin) REFERENCES wis_wahlbezirk (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE wis_erststimme ADD CONSTRAINT wis_erststimme_fuerkandidat_fkey FOREIGN KEY (fuerkandidat) REFERENCES wis_kandidatur (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
EOF

echo Generating constituency results
psql -h $HOST -U $USER -W << EOF
insert into wis_zweitstimmenergebnis 
select nextval('seq_wis_zweitstimmergebnis') sid, fuerliste, wahlkreis, anzahlstimmen , localtimestamp berechnetam, true "valid", 2 wahl
from (select zs.fuerliste, wb.wahlkreis, count(*) anzahlstimmen 
from wis_zweitstimme zs, wis_wahlbezirk wb 
WHERE zs.abgegebenin = wb.id
GROUP BY zs.fuerliste, wb.wahlkreis
) a;


insert into wis_erststimmenergebnis 
select nextval('seq_wis_erststimmergebnis') sid, fuerkandidat, wahlkreis, anzahlstimmen, localtimestamp berechnetam, true "valid" , 2 wahl 
from (
    select es.fuerkandidat, wb.wahlkreis, count(*) anzahlstimmen
    from wis_erststimme es, wis_wahlbezirk wb 
    WHERE es.abgegebenin = wb.id
    GROUP BY es.fuerkandidat, wb.wahlkreis
) a;
EOF
echo Creating views
cat statements.txt | psql -h $HOST -U $USER -W

echo Finished

