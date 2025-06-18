-- DATI_FAMIGLIA
INSERT INTO DATI_FAMIGLIA (TIPOLOGIA, DESCRIZIONE) VALUES 
('Chenopodiacee', 'Famiglia di piante senza petali, che si sviluppano spesso in ambienti o terreni salati o ricchi di nitrati.'),
('Composite', 'Famiglia importante di piante, che comprende circa 13000 specie, fra cui, principalmente, piante erbacee, ma anche alberi, arbusti o liane.'),
('Crucifere', 'Famiglia di piante che si distinguono per un fiore a quattro sepali, quattro petali a croce, sei stami, due dei quali più piccoli, e il loro frutto, detto siliqua.'),
('Cucurbitacee', 'Famiglia di piante erbacee (assai di rado arbusti), più o meno rampicanti o striscianti grazie ai loro viticci spiralati, che crescono nelle regioni a clima temperato, da caldo a tropicale.'),
('Graminacee', 'O Poaceae, sono una vasta famiglia di piante erbacee caratterizzate da fusti (culmi) cilindrici divisi in nodi e internodi, e foglie alterne composte da una guaina che avvolge il culmo e una lamina che si estende liberamente.'),
('Leguminose', 'Famiglia di piante caratterizzate dalla presenza del frutto chiamato legume o baccello, che contiene i semi.'),
('Liliacee', 'Famiglia di piante con foglie generalmente verticali e molto allungate e fiori a sei petali colorati.'),
('Ombrellifere', 'Famiglia di piante la cui caratteristica fondamentale è la disposizione dei fiori a ombrello.'),
('Solanacee', 'Famiglia di piante erbacee, arbusti, alberi o liane delle regioni con clima da temperato a tropicale.');

-- DATI_RANGE_STAGIONE
INSERT INTO DATI_RANGE_STAGIONE (MESE_SEMINA_MIN, MESE_SEMINA_MAX, STAGIONE) VALUES 
('1', '4', 'Primavera'),
('3', '6', 'Primavera/Estate'),
('7', '9', 'Estate'),
('8', '11', 'Autunno'),
('10', '12', 'Autunno/Inverno');

-- DATI_ORTAGGIO
INSERT INTO DATI_ORTAGGIO (NOME, DESCRIZIONE, GIORNI_SEMINA, GIORNI_GERMINAZIONE, GIORNI_TRAPIANTO, GIORNI_MATURAZIONE, GIORNI_RACCOLTA, PESO_MEDIO, VOLUME_MQ, ID_FAMIGLIA, ID_RANGE_STAGIONE) VALUES 
('Melanzana', 'Ortaggio estivo di colore viola', 7, 10, 20, 60, 90, 0.40, 1.20, 9, 2),
('Zucchina', 'Ortaggio primaverile, produzione veloce', 5, 7, 15, 40, 60, 0.25, 1.00, 4, 2),
('Cavolo Verza', 'Ortaggio invernale a foglia larga', 10, 12, 20, 90, 120, 1.20, 1.50, 3, 5),
('Pomodoro', 'Ortaggio estivo, rosso e succoso', 7, 10, 25, 60, 90, 0.30, 1.10, 9, 2),
('Ravanello', 'Radice rossa piccola e croccante', 3, 5, 0, 25, 30, 0.05, 0.10, 3, 1),
('Pisello', 'Legume verde dolce', 6, 9, 12, 50, 70, 0.10, 0.30, 6, 1),
('Patata', 'Tubero ricco di amido', 7, 12, 20, 90, 120, 0.30, 1.00, 9, 2),
('Porro', 'Bulbo simile alla cipolla', 8, 10, 25, 100, 130, 0.20, 0.80, 7, 4),
('Cipolla', 'Bulbo aromatico molto usato', 10, 14, 30, 120, 150, 0.25, 0.90, 7, 4),
('Melone', 'Frutto estivo dolce', 10, 12, 20, 70, 100, 1.50, 2.00, 4, 3),
('Mais', 'Cereale giallo in pannocchie', 6, 9, 18, 80, 110, 0.50, 2.50, 5, 3),
('Lattuga', 'Insalata a foglia verde', 5, 7, 10, 40, 60, 0.20, 0.60, 2, 1),
('Fagioli', 'Legume nutriente e proteico', 7, 10, 15, 60, 80, 0.30, 0.90, 6, 2),
('Spinaci', 'Foglie verdi ricche di ferro', 5, 6, 10, 30, 45, 0.15, 0.50, 1, 1),
('Scalogno', 'Bulbo delicato simile alla cipolla', 9, 11, 25, 100, 130, 0.18, 0.70, 7, 4),
('Cetriolo', 'Frutto allungato e fresco', 6, 8, 14, 50, 70, 0.25, 0.80, 4, 2),
('Sedano', 'Gambo croccante per soffritti', 8, 10, 20, 80, 100, 0.30, 1.00, 8, 2),
('Carota', 'Radice arancione dolce', 6, 9, 10, 70, 100, 0.20, 0.70, 8, 1),
('Bietola', 'Foglie verdi larghe', 7, 10, 15, 60, 90, 0.30, 1.00, 1, 2),
('Carciofo', 'Ortaggio con cuore tenero', 12, 14, 30, 150, 180, 0.35, 1.10, 2, 4),
('Asparago', 'Germoglio primaverile', 10, 12, 20, 90, 120, 0.25, 0.90, 2, 5),
('Zucca', 'Ortaggio autunnale grande', 10, 12, 20, 100, 130, 2.00, 3.00, 4, 4),
('Peperone', 'Frutto carnoso di vari colori', 8, 10, 20, 70, 100, 0.35, 1.20, 9, 2);

-- DATI_LITOLOGIA
INSERT INTO DATI_LITOLOGIA (TIPOLOGIA, DESCRIZIONE) VALUES
('Sabbioso', 'Terreno ricco di particelle di sabbia, con scarso contenuto di limo e argilla.'),
('Argilloso', 'Terreno ricco di particelle di argilla, con scarso contenuto di sabbia e limo'),
('Limoso', 'Terreno prevalentemente composto da particelle di limo, con una miscela equilibrata di sabbia e argilla.'),
('Torboso', 'Terreno caratterizzato da una alta percentuale di materia organica, in particolare torba.'),
('Salino', 'Terreno caratterizzato da un eccessivo accumulo di sali solubili, come cloruri e solfati di sodio, calcio e magnesio.'),
('Gessoso', 'Terreno caratterizzato dalla presenza di gesso (solfato di calcio biidrato.'),
('Roccioso', 'Terreno caratterizzato dalla presenza di rocce e sassi.'),
('Misto', 'Terreno che presenta una combinazione di diverse componenti, come sabbia, limo e argilla, in proporzioni che lo rendono adatto a diverse coltivazioni.');

-- DATI_AZIENDA
INSERT INTO DATI_AZIENDA (RAGIONE_SOCIALE, ANNO_FONDAZIONE, TIPOLOGIA, ATECO, NUM_DIPENDENTI) VALUES
('BioFarm S.r.l.', '2010', 'Agricoltura e attività connesse', '01.13.10', 50);

-- DATI_ECONOMICI_NAZIONALI (!DA COMPLETARE!)
INSERT INTO DATI_ECONOMICI_NAZIONALI (ANNO_RIFERIMENTO, PRODUZIONE_MEDIA, PREZZO_VENDITA_MEDIO, ID_ORTAGGIO) VALUES
('2010', 200000.0, 0.39, 1),
('2011', 200000.0, 0.42, 1),
('2012', 200000.0, 0.41, 1),
('2013', 200000.0, 0.44, 1),
('2014', 200000.0, 0.49, 1),
('2015', 200000.0, 0.51, 1),
('2016', 200000.0, 0.55, 1),
('2017', 200000.0, 0.59, 1),
('2018', 200000.0, 0.57, 1),
('2019', 200000.0, 0.59, 1),
('2020', 200000.0, 0.61, 1),
('2021', 200000.0, 0.69, 1),
('2022', 200000.0, 0.73, 1),
('2023', 200000.0, 0.76, 1),
('2024', 200000.0, 0.78, 1);

--DATI_AREA_AFFARI (!DA INSERIRE!)