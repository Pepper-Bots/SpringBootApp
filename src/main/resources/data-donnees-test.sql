INSERT INTO utilisateur(email, password)
VALUES ("a@a.com", "$2a$10$7m/yHcKoja46YkOngGNUfO8eqJpRsd4edZm3NE6LabpBzTkYMOnhm"),
       ("b@b.com", "$2a$10$7m/yHcKoja46YkOngGNUfO8eqJpRsd4edZm3NE6LabpBzTkYMOnhm"),
       ("c@c.com", "$2a$10$7m/yHcKoja46YkOngGNUfO8eqJpRsd4edZm3NE6LabpBzTkYMOnhm");


INSERT INTO vendeur(id, salaire, chef)
VALUES (1, 2000, 0),
       (3, 3000, 1);

INSERT INTO client(id, numero)
VALUES (2, "EZ1FD53EZF1");

INSERT INTO etat (id, nom)
VALUES (1, 'neuf'),
       (2, 'occasion'),
       (3, 'bon état'),
       (4, 'reconditionné'),
       (5, 'très bon état');

INSERT INTO etiquette (id, nom, couleur)
VALUES (1, 'Promo', '#77FF77'),
       (2, 'Soldes', 'red'),
       (3, 'Déstockage', 'grey'),
       (4, 'Fin de série', 'yellow'),
       (5, 'Dernière chance', 'darkred');


INSERT INTO product (id, nom, code_article, description, prix, etat_id, createur_id)
VALUES (1, 'Asus Zenbook A14', 'aza14', 'Ordinateur portable léger avec processeur Snapdragon X Elite', 1299.99, 1, 1),
       (2, 'MSI Vision X AI 2nd', 'msixa2', 'PC de bureau avec écran tactile vertical', 4099.99, 1, 1),
       (3, 'Lenovo Legion Go S', 'llgs', 'Console portable gaming sous SteamOS', 499.99, 1, 3),
       (4, 'Lenovo Legion Tab Gen 3', 'lltg3', 'Tablette gaming avec Snapdragon 8 Gen 3', 499.99, 1, 3),
       (5, 'Razer Barracuda X Chroma', 'rbxc', 'Casque gaming sans fil avec RGB', 129.99, 1, 1),
       (6, 'HyperX Cloud Alpha', 'hca', 'Casque gaming filaire avec son surround 7.1', 99.99, 5, 3),
       (7, 'Corsair HS55 Stereo', 'chs55', 'Casque gaming filaire abordable', 59.99, 3, 1),
       (8, 'Razer Viper V3 Pro', 'rvv3p', 'Souris gaming sans fil ultra-légère', 159.99, 1, 3),
       (9, 'Logitech G Pro X Superlight 2', 'lgpxs2', 'Souris gaming sans fil pour FPS', 159.99, 1, 3),
       (10, 'Lamzu Maya X', 'lmx', 'Souris gaming sans fil avec taux de sondage 8000 Hz', 89.99, 5, 3),
       (11, 'Razer BlackShark V2 Pro', 'rbsv2p', 'Casque gaming sans fil haut de gamme', 199.99, 1, 3),
       (12, 'SteelSeries Arctis Nova 7 Wireless', 'sanv7w', 'Casque gaming sans fil multiplateforme', 179.99, 4, 1),
       (13, 'Sony INZONE H5', 'sih5', 'Casque gaming sans fil pour PC et PS5', 149.99, 1, 3),
       (14, 'Razer DeathAdder V3 Pro', 'rdav3p', 'Souris gaming ergonomique sans fil', 149.99, 5, 1),
       (15, 'Corsair Katar Pro XT', 'ckpxt', 'Souris gaming filaire abordable', 29.99, 2, 1),
       (16, 'HP ZBook Ultra G1a', 'hzug1a', 'Station de travail portable puissante', 2499.99, 1, 3),
       (17, 'Lenovo ThinkPad X9 Aura Edition', 'ltx9ae', 'Ultrabook premium avec écran OLED', 1999.99, 1, 3),
       (18, 'Turtle Beach Stealth Pro', 'tbsp', 'Casque gaming sans fil avec ANC', 329.99, 1, 3),
       (19, 'Astro A50 X', 'aa50x', 'Casque gaming sans fil haut de gamme multiplateforme', 379.99, 4, 3),
       (20, 'Redragon M686 Vampire', 'rm686v', 'Souris gaming sans fil abordable', 39.99, 3, 1);

INSERT INTO etiquette_product (product_id, etiquette_id)
VALUES (1, 1),  -- Asus Zenbook A14 - Promo
       (1, 2),  -- Asus Zenbook A14 - Soldes aussi
       (3, 2),  -- Lenovo Legion Go S - Soldes
       (4, 1),  -- Lenovo Legion Tab Gen 3 - Promo
       (4, 3),  -- Lenovo Legion Tab Gen 3 - Déstockage aussi
       (6, 2),  -- HyperX Cloud Alpha - Soldes
       (7, 1),  -- Corsair HS55 Stereo - Promo
       (9, 1),  -- Logitech G Pro X Superlight 2 - Promo
       (9, 5),  -- Logitech G Pro X Superlight 2 - Dernière chance aussi
       (10, 3), -- Lamzu Maya X - Déstockage
       (12, 2), -- SteelSeries Arctis Nova 7 Wireless - Soldes
       (12, 4), -- SteelSeries Arctis Nova 7 Wireless - Fin de série aussi
       (13, 1), -- Sony INZONE H5 - Promo
       (14, 4), -- Razer DeathAdder V3 Pro - Fin de série
       (15, 2), -- Corsair Katar Pro XT - Soldes
       (15, 1), -- Corsair Katar Pro XT - Promo aussi
       (17, 1), -- Lenovo ThinkPad X9 Aura Edition - Promo
       (19, 5), -- Astro A50 X - Dernière chance
       (19, 2), -- Astro A50 X - Soldes aussi
       (20, 3); -- Redragon M686 Vampire - Déstockage

INSERT INTO commande (date)
VALUES (NOW());

INSERT INTO ligne_commande (commande_id, quantite, prix_vente, product_id)
VALUES (1, 2, 1500, 4);

INSERT INTO type_promotion (nom, pourcentage)
VALUES ('solde', 1);

INSERT INTO promotion (product_id, type_promotion_id, valeur)
VALUES (2, 1, 20);


