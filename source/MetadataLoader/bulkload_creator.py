#!/usr/bin/python
# coding=utf-8

import csv
import codecs


def read_landeslisten(filename="landeslisten.csv"):
    #fileobj = codecs.open(filename, "r", "utf-8")
    fileobj = open(filename, "r")
    csvreader = csv.reader(fileobj, delimiter=";", quotechar='"')
    landeslisten = dict()
    bundeslaender = list()
    parteien = list()
    next(csvreader)
    for row in csvreader:
        listnum, land, partei = row
        if not land in bundeslaender:
            bundeslaender.append(land)
        if not partei in parteien:
            parteien.append(partei)
        landeslisten[listnum] = (land, partei)
    fileobj.close()
    return landeslisten, bundeslaender, parteien


def read_wahlkreise(filename="StruktBtwkr2009.csv"):
    fileobj = open(filename, "r")
    csvreader = csv.reader(fileobj, delimiter=";", quotechar='"')
    # header-zeilen überspringen
    next(csvreader)
    next(csvreader)
    next(csvreader)
    next(csvreader)
    next(csvreader)
    wahlkreise = dict()
    for row in csvreader:
        land, num, name = row[0:3]
        if name.startswith("Land insgesamt") or name.startswith("Insgesamt"):
            continue
        wahlkreise[num] = (land, name)
    fileobj.close()
    return wahlkreise


def read_kandidaten(parteien, filename="wahlbewerber.csv"):
    fileobj = open(filename, "r")
    csvreader = csv.reader(fileobj, delimiter=";", quotechar='"')
    # header-zeile überspringen
    next(csvreader)
    kandidaten = list()
    for row in csvreader:
        titel, vorname, nachname, partei, jahrgang, num = row
        if titel != "":
            kandidaten.append((titel+" "+vorname, nachname, partei, jahrgang, num))
        else:
            kandidaten.append((vorname, nachname, partei, jahrgang, num))
        if partei != '' and partei not in parteien:
            parteien.append(partei)
    fileobj.close()
    return kandidaten


def read_listenkandidaturen(filename="listenplaetze.csv"):
    fileobj = open(filename, "r")
    csvreader = csv.reader(fileobj, delimiter=";", quotechar='"')
    # header-zeile überspringen
    next(csvreader)
    listenkandidaturen = list()
    for row in csvreader:
        liste, kandidat, pos = row
        listenkandidaturen.append((liste, kandidat, pos))
    fileobj.close()
    return listenkandidaturen


def read_direktkandidaturen(filename="wahlbewerber2009_mod.csv"):
    fileobj = open(filename, "r")
    csvreader = csv.reader(fileobj, delimiter=";", quotechar='"')
    # header-zeile überspringen
    next(csvreader)
    dkand = list()
    for row in csvreader:
        vorname, nachname, jahrgang, partei, wahlkreis, land, platz = row
        if wahlkreis != "": # Direktkandidat
            dkand.append((vorname, nachname, jahrgang, partei, wahlkreis))
    fileobj.close()
    return dkand


bland_mapping = {
    'BW': 'Baden-W\xc3\xbcrttemberg',
    'BY': 'Bayern',
    'BE': 'Berlin',
    'BB': 'Brandenburg',
    'HB': 'Bremen',
    'HH': 'Hamburg',
    'HE': 'Hessen',
    'MV': 'Mecklenburg-Vorpommern',
    'NI': 'Niedersachsen',
    'NW': 'Nordrhein-Westfalen',
    'RP': 'Rheinland-Pfalz',
    'SL': 'Saarland',
    'SN': 'Sachsen',
    'ST': 'Sachsen-Anhalt',
    'SH': 'Schleswig-Holstein',
    'TH': 'Th\xc3\xbcringen',
    }


def read_kandidaten2005(filename="wahlbewerber2005.csv"):
    fileobj = open(filename, "r")
    csvreader = csv.reader(fileobj, delimiter=";", quotechar='"')
    # header-zeile überspringen
    next(csvreader)
    kand = list()
    for row in csvreader:
        nachname, vorname = row[0].split(", ")
        jahrgang = row[2]
        partei = row[7].strip()
        if partei == 'Anderer KWV':
            partei = 'Unab'
        wahlkreis = row[8]
        land = row[9]
        if land != '':
            land = bland_mapping[land]
        platz = row[10]
        kand.append((vorname, nachname, partei, jahrgang, wahlkreis, land, platz)) 
    return kand


def create_bulkload():
    landeslisten, bundeslaender, parteien = read_landeslisten()
    wahlkreise = read_wahlkreise()
    kandidaten = read_kandidaten(parteien)
    lkand = read_listenkandidaturen()
    dkand = read_direktkandidaturen()
    kand2005 = read_kandidaten2005()
    d_land = dict()
    d_partei = dict()
    # Bundesländer
    f = open("import_wis_bundesland.csv", "w")
    id = 1
    for land in bundeslaender:
        f.write(u"%d;" % id)
        f.write(land)
        f.write("\n")
        d_land[land] = id
        id += 1
    f.close()

    # Parteien
    f = open("import_wis_partei.csv", "w")
    id = 1
    for partei in parteien:
        f.write(u"%d;" % id)
        f.write(partei)
        f.write("\n")
        d_partei[partei] = id
        id += 1
    f.write("%d;Unab\n" % id)
    d_partei['Unab'] = id
    id += 1
    for row in kand2005:
        partei = row[2]
        if not d_partei.has_key(partei):
            f.write(u"%d;" % id)
            f.write(partei)
            f.write("\n")
            d_partei[partei] = id
            id += 1
    f.close()

    d_landeslisten = dict()
    d_landeslisten_name = dict()
    # Landeslisten
    f = open("import_wis_landesliste.csv", "w")
    id = 1
    for num, val in landeslisten.iteritems():
        land, partei = val
        # 2005
        f.write("%d;" % id)
        f.write(partei)
        f.write(" ")
        f.write(land)
        f.write(";%d;%d;%d\n" % (d_partei[partei], 1, d_land[land]))
        d_landeslisten[(int(num), 1)] = id
        d_landeslisten_name[(land, partei)] = id
        id += 1
        # 2009
        f.write("%d;" % id)
        f.write(partei)
        f.write(" ")
        f.write(land)
        f.write(";%d;%d;%d\n" % (d_partei[partei], 2, d_land[land]))
        d_landeslisten[(int(num), 2)] = id
        id += 1
    for row in kand2005:
        partei = row[2]
        land = row[5]
        if land != '' and not d_landeslisten_name.has_key((land, partei)):
            f.write("%d;" % id)
            f.write(partei)
            f.write(" ")
            f.write(land)
            f.write(";%d;%d;%d\n" % (d_partei[partei], 1, d_land[land]))
            d_landeslisten_name[(land, partei)] = id
            id += 1
    f.close()

    # Wahlkreise
    fwk = open("import_wis_wahlkreis.csv", "w")
    fwb = open("import_wis_wahlbezirk.csv", "w")
    for num, val in wahlkreise.iteritems():
        land, name = val
        if land == 'Baden-W\xef\xbf\xbdrttemberg':
            land = 'Baden-W\xc3\xbcrttemberg'
        if land == 'Th\xef\xbf\xbdringen':
            land = 'Th\xc3\xbcringen' 
        fwk.write("%d;" % int(num))
        fwk.write(name)
        fwk.write(";;%d\n" % d_land[land])
        fwb.write("%d;" % int(num))
        fwb.write(name)
        fwb.write(";;;%d\n" % int(num))
    fwk.close()
    fwb.close()
    
    # Kandidaten
    d_kandidat = dict()
    f = open("import_wis_kandidat.csv", "w")
    idkand = 0
    for vorname, nachname, partei, jahrgang, num in kandidaten:
        f.write("%d;" % int(num))
        f.write(vorname)
        f.write(";")
        f.write(nachname)
        if partei == '': partei = "Unab"
        f.write(";%d\n" % d_partei[partei])
        d_kandidat[(vorname, nachname, jahrgang)] = num
        if int(num) > idkand:
            idkand = int(num)
    idkand += 1
    for vorname, nachname, partei, jahrgang, wahlkreis, land, platz in kand2005:
        if not d_kandidat.has_key((vorname, nachname, jahrgang)):
            f.write("%d;" % idkand)
            f.write(vorname)
            f.write(";")
            f.write(nachname)
            if partei == '': partei = "Unab"
            f.write(";%d\n" % d_partei[partei])
            d_kandidat[(vorname, nachname, jahrgang)] = idkand 
            idkand += 1
    f.close() 
    
    kandidaturen = dict()
    # Listenkandidaten 2009
    for liste, kandidat, pos in lkand:
        kandidaturen[int(kandidat)] = [int(kandidat), int(liste), int(pos), None]

    # Direktkandidaten 2009
    for vorname, nachname, jahrgang, partei, wahlkreis in dkand:
        kandidat = d_kandidat[(vorname, nachname, jahrgang)]
        if not int(kandidat) in kandidaturen.keys():
            kandidaturen[int(kandidat)] = [int(kandidat), None, None, int(wahlkreis)]
        else:
            kandidaturen[int(kandidat)][3] = int(wahlkreis)


    f = open("import_wis_kandidatur.csv", "w")
    id = 1
    # Kandidaturen 2005
    for vorname, nachname, partei, jahrgang, wahlkreis, land, platz in kand2005:
        kandidat = d_kandidat[(vorname, nachname, jahrgang)]
        if land != '':
            liste = d_landeslisten_name[(land, partei)]
        f.write("%d;1;%s;%s;%s;%s\n" % (id, kandidat, liste, platz, wahlkreis))
        id += 1 

    # Kandidaturen 2009
    for key, val in kandidaturen.iteritems():
        kand, liste, pos, wahlkreis = val
        if wahlkreis is None: wahlkreis = ''
        if liste is None: liste = ''; pos = ''
        if liste != '':
            #liste1 = d_landeslisten[(int(liste), 1)]
            liste2 = d_landeslisten[(int(liste), 2)]
        else:
            #liste1 = ''
            liste2 = ''
        #f.write("%d;1;%s;%s;%s;%s\n" % (id, kand, liste1, pos, wahlkreis))
        #id += 1
        f.write("%d;2;%s;%s;%s;%s\n" % (id, kand, liste2, pos, wahlkreis))
        id += 1

    f.close()


create_bulkload()


