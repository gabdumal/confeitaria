BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Produto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"tipo"	INTEGER NOT NULL DEFAULT 0 CHECK("tipo" IN (0, 1)),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "ProdutoPronto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL UNIQUE,
	"estoque"	INTEGER NOT NULL DEFAULT 0,
	"valor"	REAL NOT NULL DEFAULT 0,
	FOREIGN KEY("id") REFERENCES "Produto"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ProdutoPersonalizado" (
	"id"	INTEGER NOT NULL UNIQUE,
	"detalhe"	TEXT,
	FOREIGN KEY("id") REFERENCES "Produto"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
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
	FOREIGN KEY("id") REFERENCES "Endereco"("id") ON DELETE CASCADE,
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
CREATE TABLE IF NOT EXISTS "Cliente" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idEndereco"	INTEGER NOT NULL,
	"cartao"	TEXT NOT NULL CHECK(LENGTH("cartao") == 16),
	FOREIGN KEY("idEndereco") REFERENCES "Endereco"("id") ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Funcionario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"matricula"	TEXT NOT NULL UNIQUE,
	"funcao"	TEXT NOT NULL,
	FOREIGN KEY("id") REFERENCES "Usuario"("id") ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Caracteristica" (
	"id"	INTEGER NOT NULL UNIQUE,
	"tipo"	TEXT NOT NULL CHECK("tipo" IN ("F", "R", "T", "C")),
	"nome"	TEXT NOT NULL UNIQUE,
	"valorGrama"	REAL NOT NULL DEFAULT 0,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Forma" (
	"id"	INTEGER NOT NULL UNIQUE,
	"recheios"	INTEGER NOT NULL DEFAULT 0,
	"gramaRecheio"	INTEGER NOT NULL DEFAULT 0,
	"gramaCobertura"	INTEGER NOT NULL DEFAULT 0,
	"gramaMassa"	INTEGER NOT NULL DEFAULT 0,
	FOREIGN KEY("id") REFERENCES "Caracteristica"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ProdutoPersonalizado_Caracteristica" (
	"idProdutoPersonalizado"	INTEGER NOT NULL UNIQUE,
	"idCaracteristica"	INTEGER NOT NULL UNIQUE,
	FOREIGN KEY("idCaracteristica") REFERENCES "Caracteristica"("id") ON DELETE CASCADE,
	FOREIGN KEY("idProdutoPersonalizado") REFERENCES "Produto"("id") ON DELETE CASCADE,
	PRIMARY KEY("idProdutoPersonalizado","idCaracteristica")
);
INSERT INTO "Produto" ("id","tipo") VALUES (1,1);
INSERT INTO "Produto" ("id","tipo") VALUES (2,1);
INSERT INTO "Produto" ("id","tipo") VALUES (3,0);
INSERT INTO "Produto" ("id","tipo") VALUES (4,0);
INSERT INTO "Produto" ("id","tipo") VALUES (5,0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (3,'Brownie',8,6.95);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (4,'Bolo de milho simples',1,7.89);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (5,'Sorvete de manga apimentada',0,3.67);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe") VALUES (1,'Gostoso');
INSERT INTO "Transacao" ("id","valor","diaHora","descricao") VALUES (0,12.0,'2020-08-17T10:11:16.908732','Transação teste');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","admin","email","telefone") VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","admin","email","telefone") VALUES (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476');
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (1,'R','Creme de morango',0.32);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (2,'F','Redonda 20cm',0.0625);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (2,1,100,150,800);
INSERT INTO "ProdutoPersonalizado_Caracteristica" ("idProdutoPersonalizado","idCaracteristica") VALUES (1,1);
COMMIT;
