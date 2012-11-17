create table wis_bundesland (
        id integer primary key,
        name varchar(100)
);

create table wis_partei (
        id integer primary key,
        name varchar(1000),
        kurzname varchar(10),
        nationaleminderheit boolean
);

create table wis_wahlkreis (
        id integer primary key,
        name varchar(500),
        ort varchar(1000),
        bundesland integer references wis_bundesland
);

create table wis_wahlbezirk (
        id integer primary key,
        name varchar(500),
        ort varchar(1000),
        sitzwahllokal varchar(1000),
        wahlkreis integer references wis_wahlkreis
);

create table wis_bundestagswahl (
        id integer primary key,
        jahr integer not null unique
);
     
create table wis_kandidat (
	id integer primary key,
	vorname varchar(100) not null,
	nachname varchar(100) not null,
	partei integer references wis_partei null
);

create table wis_landesliste (
	id integer primary key,
	name varchar(100) not null,
	partei integer references wis_partei,
	wahl integer references wis_bundestagswahl not null,
	bundesland integer references wis_bundesland not null
);

create table wis_kandidatur (
	id integer primary key,
	wahl integer references wis_bundestagswahl not null,
	kandidat integer references wis_kandidat not null,
	landesliste integer references wis_landesliste null,
	listenplatz integer null,
	wahlkreis integer references wis_wahlkreis null,
	partei integer references wis_partei
);

create table wis_erststimme (
	id integer primary key,
	fuerkandidat integer references wis_kandidatur not null,
	abgegebenin integer references wis_wahlbezirk not null
);

create table wis_zweitstimme (
	id integer primary key,
	fuerliste integer references wis_landesliste not null,
	abgegebenin integer references wis_wahlbezirk not null
);

create table wis_erststimmenergebnis (
	id integer primary key,
	fuerkandidatur integer references wis_kandidatur not null,
	inwahlkreis integer references wis_wahlkreis not null,
	anzahlstimmen integer not null,
	berechnetam timestamp not null,
	valid boolean default true,
	wahl integer references wis_bundestagswahl
);

create table wis_zweitstimmenergebnis (
	id integer primary key,
	fuerliste integer references wis_landesliste not null,
	inwahlkreis integer references wis_wahlkreis not null,
	anzahlstimmen integer not null,
	berechnetam timestamp not null,
	valid boolean default true,
	wahl integer references wis_bundestagswahl
);

create sequence seq_wis_erststimmergebnis increment 1 minvalue 1 start 1;
create sequence seq_wis_zweitstimmergebnis increment 1 minvalue 1 start 1;

