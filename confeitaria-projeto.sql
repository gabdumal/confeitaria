BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Produto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL UNIQUE,
	"valor"	REAL NOT NULL,
	"quantidade"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Item" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valorTotal"	REAL NOT NULL,
	"quantidade"	INTEGER NOT NULL,
	"idProduto"	INTEGER NOT NULL,
	FOREIGN KEY("idProduto") REFERENCES "Produto"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Pedido" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valorTotal"	REAL NOT NULL,
	"estado"	TEXT NOT NULL CHECK("estado" IN ('S', 'A', 'F', 'C')),
	"agendado"	INTEGER NOT NULL DEFAULT 0,
	"dataSolicitacao"	TEXT,
	"dataEntrega"	TEXT,
	"comentario"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Usuario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL,
	"nomeUsuario"	TEXT NOT NULL UNIQUE,
	"senhaHash"	TEXT NOT NULL,
	"admin"	INTEGER NOT NULL DEFAULT 0 CHECK("admin" IN (0, 1)),
	"email"	TEXT NOT NULL UNIQUE,
	"telefone"	TEXT NOT NULL,
	"endereco"	TEXT NOT NULL,
	"cartao"	TEXT NOT NULL,
	"identificador"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Transacao" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valor"	REAL NOT NULL,
	"diaHora"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
INSERT INTO "Produto" VALUES (2,'Brownie',99.63,14);
INSERT INTO "Produto" VALUES (3,'Sorvete de Manga',2.35,0);
INSERT INTO "Produto" VALUES (5,'Torta de banana',2.0,2);
INSERT INTO "Produto" VALUES (6,'Cupcake de morango',5.89,1);
INSERT INTO "Usuario" VALUES (2,'Bruno','brunin','senha',0,'bruno@gmail.com','454545454','casa','12345678912','12345678912');
INSERT INTO "Usuario" VALUES (3,'Ana Mary','anamary','senha',1,'anamaryb@gmail.com','32978787878','Uma casa bem bonita','123456789012345','12345678901');
INSERT INTO "Usuario" VALUES (4,'Rodrigo Soares','RodRod','senha',1,'rodrigo@gmail.com','24988679969','minhacasa','1234123412341234','111222333-8');
INSERT INTO "Usuario" VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454','Rua dos usuários, 34 - Bairro Residencial, Populópolis - PV','9287873465762356','347.726.872-78');
INSERT INTO "Usuario" VALUES (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476','Avenida dos funcionários, 1976 - Vale das empresas, Populópolis - PV','1983467167309864','123.987.758-62');
INSERT INTO "Transacao" VALUES (1,12.0,'2020-08-17T10:11:16.908732','teste teste');
COMMIT;
