BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Produto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL UNIQUE,
	"valor"	REAL NOT NULL,
	"quantidade"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Transacao" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valor"	REAL NOT NULL,
	"diaHora"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Endereco" (
	"id"	INTEGER NOT NULL UNIQUE,
	"numero"	TEXT NOT NULL DEFAULT 'S/N',
	"complemento"	TEXT,
	"logradouro"	TEXT NOT NULL,
	"bairro"	TEXT NOT NULL,
	"cidade"	TEXT NOT NULL,
	"uf"	TEXT NOT NULL,
	"cep"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Cliente" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idEndereco"	INTEGER NOT NULL,
	"cartao"	TEXT NOT NULL CHECK(LENGTH("cartao") == 16),
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("idEndereco") REFERENCES "Endereco"("id")
);
CREATE TABLE IF NOT EXISTS "Usuario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL,
	"nomeUsuario"	TEXT NOT NULL UNIQUE,
	"senhaHash"	TEXT NOT NULL,
	"admin"	INTEGER NOT NULL DEFAULT 0 CHECK("admin" IN (0, 1)),
	"email"	TEXT NOT NULL UNIQUE,
	"telefone"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Funcionario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"matricula"	TEXT NOT NULL UNIQUE,
	"funcao"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("id") REFERENCES "Usuario"("id")
);
CREATE TABLE IF NOT EXISTS "ProdutoPersonalizado" (
	"id"	INTEGER NOT NULL UNIQUE,
	"recheio"	TEXT NOT NULL,
	"cobertura"	TEXT NOT NULL,
	"detalhe"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Produto"("id")
);
INSERT INTO "Produto" VALUES (0,'Brownie',99.63,16);
INSERT INTO "Produto" VALUES (1,'Sorvete de Manga',2.35,0);
INSERT INTO "Produto" VALUES (2,'Torta de banana',2.0,2);
INSERT INTO "Produto" VALUES (3,'Cupcake de morango',5.89,1);
INSERT INTO "Produto" VALUES (4,'Banana split',76.7,3);
INSERT INTO "Transacao" VALUES (0,12.0,'2020-08-17T10:11:16.908732','teste teste');
INSERT INTO "Usuario" VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454');
INSERT INTO "Usuario" VALUES (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476');
COMMIT;
