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
CREATE TABLE IF NOT EXISTS "Produto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valor"	REAL NOT NULL DEFAULT 0,
	"tipo"	INTEGER NOT NULL DEFAULT 0 CHECK("tipo" IN (0, 1)),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "ProdutoPronto" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL UNIQUE,
	"estoque"	INTEGER NOT NULL DEFAULT 0,
	FOREIGN KEY("id") REFERENCES "Produto"("id"),
	PRIMARY KEY("id")
);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao") VALUES (1,150.0,'2022-12-07T15:13:20.726762437','30kg de farinha fermentada');
INSERT INTO "Transacao" ("id","valor","diaHora","descricao") VALUES (2,95.0,'2022-12-07T15:14:12.426938979','Venda presencial : Bolo de chocolate');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","admin","email","telefone") VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","admin","email","telefone") VALUES (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476');
INSERT INTO "ProdutoPersonalizado" ("id","recheio","cobertura","detalhe") VALUES (6,'Chocolate meio amargo','Glacê de limão','');
INSERT INTO "ProdutoPersonalizado" ("id","recheio","cobertura","detalhe") VALUES (9,'Chocolate meio amargo','Chantilly','dsdas');
INSERT INTO "Produto" ("id","valor","tipo") VALUES (6,100.0,0);
INSERT INTO "Produto" ("id","valor","tipo") VALUES (9,100.0,0);
INSERT INTO "Produto" ("id","valor","tipo") VALUES (11,5.56,0);
INSERT INTO "Produto" ("id","valor","tipo") VALUES (12,7.897,0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque") VALUES (11,'Brownie',8);
INSERT INTO "ProdutoPronto" ("id","nome","estoque") VALUES (12,'Sorvete de manga apimentada',0);
COMMIT;
