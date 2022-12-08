BEGIN TRANSACTION;
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
	FOREIGN KEY("idEndereco") REFERENCES "Endereco"("id"),
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
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Funcionario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"matricula"	TEXT NOT NULL UNIQUE,
	"funcao"	TEXT NOT NULL,
	FOREIGN KEY("id") REFERENCES "Usuario"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "ProdutoPersonalizado" (
	"id"	INTEGER NOT NULL UNIQUE,
	"recheio"	TEXT NOT NULL,
	"cobertura"	TEXT NOT NULL,
	"detalhe"	TEXT,
	FOREIGN KEY("id") REFERENCES "Produto"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ProdutoPronto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL UNIQUE,
	"quantidade"	INTEGER NOT NULL DEFAULT 0,
	FOREIGN KEY("id") REFERENCES "Produto"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Produto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valor"	REAL NOT NULL DEFAULT 0,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao") VALUES (1,150.0,'2022-12-07T15:13:20.726762437','30kg de farinha fermentada'),
 (2,95.0,'2022-12-07T15:14:12.426938979','Venda presencial : Bolo de chocolate');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","admin","email","telefone") VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454'),
 (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476');
INSERT INTO "ProdutoPersonalizado" ("id","recheio","cobertura","detalhe") VALUES (6,'Chocolate meio amargo','Glacê de limão',''),
 (7,'Chocolate meio amargo','Glacê de limão',''),
 (8,'Chocolate meio amargo','Glacê de limão',''),
 (9,'Chocolate meio amargo','Chantilly','dsdas');
INSERT INTO "Produto" ("id","valor") VALUES (0,99.63),
 (1,2.35),
 (2,2.0),
 (3,5.89),
 (4,76.7),
 (6,100.0),
 (7,100.0),
 (8,100.0),
 (9,100.0),
 (10,10.0);
COMMIT;
