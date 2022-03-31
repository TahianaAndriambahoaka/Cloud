DROP TABLE facturation;
DROP TABLE reception;
DROP TABLE livraison;
DROP TABLE commande;
DROP TABLE detailProformat;
DROP TABLE proformat;
DROP TABLE relAchatFournisseur;
DROP TABLE relAchatBesoin;
DROP TABLE besoin;
DROP TABLE achat;
DROP TABLE produit;
DROP TABLE fournisseur;
DROP TABLE client;

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(20)
);

CREATE TABLE fournisseur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(20)
);

CREATE TABLE produit (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(20),
    type VARCHAR(20)
);

CREATE TABLE achat (
    id SERIAL PRIMARY KEY,
    date DATE,
    statut VARCHAR(10)
);

CREATE TABLE besoin (
    id SERIAL PRIMARY KEY,
    idProduit INT REFERENCES produit(id),
    quantite INT
);

CREATE TABLE relAchatBesoin (
    id SERIAL PRIMARY KEY,
    idAchat INT REFERENCES achat(id),
    idBesoin INT REFERENCES besoin(id)
);

CREATE TABLE relAchatFournisseur (
    id SERIAL PRIMARY KEY,
    idAchat INT REFERENCES achat(id),
    idFournisseur INT REFERENCES fournisseur(id)
);

CREATE TABLE proformat (
    id SERIAL PRIMARY KEY,
    idClient INT REFERENCES client(id),
    idAchat INT REFERENCES achat(id),
    date DATE
);

CREATE TABLE detailProformat (
    id SERIAL PRIMARY KEY,
    idBesoin INT REFERENCES besoin(id),
    prixunitaire DOUBLE PRECISION,
    idProformat INT REFERENCES proformat(id)
);

CREATE TABLE commande (
    id SERIAL PRIMARY KEY,
    idProformat INT REFERENCES proformat(id),
    date DATE,
    statut VARCHAR(10)
);

CREATE TABLE livraison (
    id SERIAL PRIMARY KEY,
    idCommande INT REFERENCES commande(id),
    date DATE
);

CREATE TABLE reception (
    id SERIAL PRIMARY KEY,
    idCommande INT REFERENCES commande(id),
    date DATE
);

CREATE TABLE facturation (
    id SERIAL PRIMARY KEY,
    idCommande INT REFERENCES commande(id),
    date DATE
);

INSERT INTO client VALUES
(default, 'Client 1'),
(default, 'Client 2');

INSERT INTO produit VALUES
(default, 'Produit 1', 'Type 1'),
(default, 'Produit 2', 'Type 2'),
(default, 'Produit 3', 'Type 3'),
(default, 'Produit 4', 'Type 4'),
(default, 'Produit 5', 'Type 5'),
(default, 'Produit 6', 'Type 6'),
(default, 'Produit 7', 'Type 7'),
(default, 'Produit 8', 'Type 8'),
(default, 'Produit 9', 'Type 9'),
(default, 'Produit 10', 'Type 10');

INSERT INTO besoin VALUES
(default, 1, 10),
(default, 2, 1),
(default, 3, 14),
(default, 4, 9),
(default, 5, 26),
(default, 6, 5),
(default, 7, 17),
(default, 8, 29),
(default, 9, 14),
(default, 10, 7);

INSERT INTO achat VALUES
(default, '2022-02-18', 'En_attente'),
(default, '2022-02-19', 'En_attente');

INSERT INTO relAchatBesoin VALUES
(default, 1, 1),
(default, 1, 2),
(default, 1, 3),
(default, 1, 4),
(default, 1, 5),
(default, 1, 6),
(default, 1, 7),
(default, 1, 8),
(default, 1, 9),
(default, 1, 10),
(default, 2, 1),
(default, 2, 2),
(default, 2, 3),
(default, 2, 4),
(default, 2, 5),
(default, 2, 6),
(default, 2, 7),
(default, 2, 8),
(default, 2, 9),
(default, 2, 10);

INSERT INTO proformat VALUES
(default, 1, 1, '2022-02-18'),
(default, 2, 2, '2022-02-19');

INSERT INTO commande VALUES
(default, 1, '2022-02-02', 'En_attente');

INSERT INTO reception VALUES
(default, 1, '2022-02-02');

-- liste demandes de pro forma
select  DISTINCT(pf.id) as idProForma, c.id as idClient, c.nom as client, pf.date, a.statut
        from achat a
        join proformat pf on (pf.idAchat = a.id)
        join client c on (c.id = pf.idClient);
        
-- details demande de pro forma
select  p.id as idProduit, p.nom, p.type, b.quantite
        from relAchatBesoin rab
        join achat a on (a.id = rab.idAchat)
        join besoin b on (b.id = rab.idBesoin)
        join produit p on (p.id = b.idProduit)
        join proformat pf on (pf.idAchat = a.id)
        where pf.id = 1;

-- select achat where idproforma...
select  a.*
        from achat a
        join proformat pf on (pf.idAchat = a.id)
        where pf.id = 1;

-- liste bon de commande
select  DISTINCT(co.id) as idCommande, c.id as idClient, c.nom as client, co.date, co.statut
        from achat a
        join proformat pf on (pf.idAchat = a.id)
        join client c on (c.id = pf.idClient)
        join commande co on (co.idProFormat = pf.id);
        
-- details bon de commande
select  p.id as idProduit, p.nom, p.type, b.quantite
        from relAchatBesoin rab
        join achat a on (a.id = rab.idAchat)
        join besoin b on (b.id = rab.idBesoin)
        join produit p on (p.id = b.idProduit)
        join proformat pf on (pf.idAchat = a.id)
        join commande c on (c.idProFormat = pf.id)
        where c.id = 1;
        
-- liste bon de réception
select  DISTINCT(r.id) as idReception, c.id as idClient, c.nom as client, co.date,
		case
			when f.id is null then 'En_attente'
			else 'Envoye'
		END statut
        from achat a
        join proformat pf on (pf.idAchat = a.id)
        join client c on (c.id = pf.idClient)
        join commande co on (co.idProFormat = pf.id)
        join reception r on (r.idCommande = co.id)
        left join facturation f on (f.idCommande = co.id);
        
-- details bon de commande
select  p.id as idProduit, p.nom, p.type, b.quantite, c.id as idCommande
        from relAchatBesoin rab
        join achat a on (a.id = rab.idAchat)
        join besoin b on (b.id = rab.idBesoin)
        join produit p on (p.id = b.idProduit)
        join proformat pf on (pf.idAchat = a.id)
        join commande c on (c.idProFormat = pf.id)
        join reception r on (r.idCommande = c.id)
        where r.id = 1;
        