INSERT INTO famille(code, libelle) VALUES
("ADC", "Adiclic"),
("AFTA", "AfterAfect"),
("AA", "Antalgiques et association"),
("AI", "Anti-Inflammatoire"),
("ATCP", "Anticéptique"),
("ART", "Arant"),
("DR", "Derimer"),
("EF", "Efter-2");

INSERT INTO composant(code, libelle) VALUES
("AE28", "Areosol"),
("ANT", "Anteriol"),
("AERF", "Efter"),
("IJJT", "Irriji"),
("OKU", "Okaïu"),
("PRC", "Paracétamol"),
("REAF", "Refard");

INSERT INTO medicament(depotLegal, nomCommercial, effets, contreIndic, prixEchantillion, stocks, famCode) VALUES
("XBD", "Andrexol", "Répare les bobos", "ne pas utiliser pour blessé", 10.0, 50, "AI"),
("SPRY", "Anticéspray", "Désinfectant", "", 1.99, 121, "AI");

INSERT INTO constituer(depotLegal, code) VALUES
("XBD", "AE28"),
("XBD", "AERF"),
("XBD", "PRC"),
("SPRY", "AE28"),
("SPRY", "IJJT"),
("SPRY", "REAF"),
("SPRY", "PRC");