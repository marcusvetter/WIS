#!/bin/bash
CSVPATH=/home/postgresql/metadataloader

#python bulkload_creator.py

psql wis << EOF
TRUNCATE TABLE wis_partei, wis_bundesland, wis_bundestagswahl, wis_kandidat, wis_landesliste CASCADE;

copy wis_partei (id, name) from '$CSVPATH/import_wis_partei.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_bundesland (id, name) from '$CSVPATH/import_wis_bundesland.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_wahlkreis (id, name, ort, bundesland) from '$CSVPATH/import_wis_wahlkreis.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_wahlbezirk (id, name, ort, sitzwahllokal, wahlkreis) from '$CSVPATH/import_wis_wahlbezirk.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_bundestagswahl (id, jahr) from '$CSVPATH/import_wis_bundestagswahl.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_kandidat (id, vorname, nachname, partei) from '$CSVPATH/import_wis_kandidat.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_landesliste (id, name, partei, wahl, bundesland) from '$CSVPATH/import_wis_landesliste.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

copy wis_kandidatur (id, wahl, kandidat, landesliste, listenplatz, wahlkreis) from '$CSVPATH/import_wis_kandidatur.csv' WITH DELIMITER AS ';' NULL AS '' CSV;

EOF
