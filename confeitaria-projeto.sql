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
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Produto"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "ProdutoPersonalizado" (
	"id"	INTEGER NOT NULL UNIQUE,
	"detalhe"	TEXT,
	"receita"	TEXT NOT NULL CHECK("receita" IN ('B', 'T')),
	"idCobertura"	INTEGER,
	"idCor"	INTEGER NOT NULL,
	"idForma"	INTEGER NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Produto"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Transacao" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valor"	REAL NOT NULL,
	"diaHora"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	"ehPedido"	INTEGER NOT NULL DEFAULT 0 CHECK("ehPedido" IN (0, 1)),
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
	"cep"	TEXT NOT NULL CHECK(LENGTH("cep") == 7),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Usuario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nome"	TEXT NOT NULL,
	"nomeUsuario"	TEXT NOT NULL UNIQUE,
	"senhaHash"	TEXT NOT NULL,
	"identificador"	TEXT NOT NULL,
	"email"	TEXT NOT NULL UNIQUE,
	"telefone"	TEXT NOT NULL,
	"admin"	INTEGER NOT NULL DEFAULT 0 CHECK("admin" IN (0, 1)),
	"idEndereco"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("idEndereco") REFERENCES "Endereco"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Cliente" (
	"id"	INTEGER NOT NULL UNIQUE,
	"cartao"	TEXT NOT NULL CHECK(LENGTH("cartao") == 16),
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Usuario"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "PessoaFisica" (
	"id"	INTEGER NOT NULL UNIQUE,
	"dataNascimento"	TEXT NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Cliente"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "PessoaJuridica" (
	"id"	INTEGER NOT NULL UNIQUE,
	"razaoSocial"	TEXT NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Cliente"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Funcionario" (
	"id"	INTEGER NOT NULL UNIQUE,
	"matricula"	TEXT NOT NULL UNIQUE,
	"funcao"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("id") REFERENCES "Usuario"("id") ON DELETE CASCADE
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
	"recheios"	INTEGER NOT NULL DEFAULT 0 CHECK("recheios" >= 0 AND "recheios" <= 3),
	"gramaRecheio"	INTEGER NOT NULL DEFAULT 0,
	"gramaCobertura"	INTEGER NOT NULL DEFAULT 0,
	"gramaMassa"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Caracteristica"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "ProdutoPersonalizado_Recheio" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idProdutoPersonalizado"	INTEGER NOT NULL,
	"idRecheio"	INTEGER NOT NULL,
	FOREIGN KEY("idProdutoPersonalizado") REFERENCES "Produto"("id") ON DELETE CASCADE,
	FOREIGN KEY("idRecheio") REFERENCES "Caracteristica"("id") ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Pedido" (
	"id"	INTEGER NOT NULL UNIQUE,
	"estado"	TEXT NOT NULL,
	"dataEntrega"	TEXT NOT NULL,
	"comentario"	TEXT,
	"idCliente"	INTEGER NOT NULL,
	FOREIGN KEY("idCliente") REFERENCES "Usuario"("id"),
	FOREIGN KEY("id") REFERENCES "Transacao"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Item" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valorTotal"	REAL NOT NULL,
	"quantidade"	INTEGER NOT NULL,
	"idProduto"	INTEGER NOT NULL,
	"idPedido"	INTEGER NOT NULL,
	FOREIGN KEY("idPedido") REFERENCES "Pedido"("id") ON DELETE CASCADE,
	FOREIGN KEY("idProduto") REFERENCES "Produto"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "Produto" ("id","tipo") VALUES (1,1);
INSERT INTO "Produto" ("id","tipo") VALUES (2,1);
INSERT INTO "Produto" ("id","tipo") VALUES (3,0);
INSERT INTO "Produto" ("id","tipo") VALUES (4,0);
INSERT INTO "Produto" ("id","tipo") VALUES (5,0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (3,'Brownie',8,6.95);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (4,'Bolo de milho simples',1,7.89);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (5,'Sorvete de manga apimentada',0,3.67);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCobertura","idCor","idForma") VALUES (1,'Gostoso','B',4,3,2);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (0,12.0,'2020-08-17T10:11:16.908732','Transação teste',0);
INSERT INTO "Endereco" ("id","numero","complemento","logradouro","bairro","cidade","uf","cep") VALUES (1,'0','','Rua dos bobos','Vale Místico','Lírica do Norte','MG','3789287');
INSERT INTO "Endereco" ("id","numero","complemento","logradouro","bairro","cidade","uf","cep") VALUES (2,'34','APT 506','Avenida Brasil','Centro','Mar de Espanha','MG','3897235');
INSERT INTO "Endereco" ("id","numero","complemento","logradouro","bairro","cidade","uf","cep") VALUES (3,'S/N','','Rua das Indústrias','Distrito Industrial','Juiz de Fora','MG','3809725');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","identificador","email","telefone","admin","idEndereco") VALUES (1,'Cliente PF Exemplo','cliente','senha','01234567890','cliente@email.com','32980675454',0,1);
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","identificador","email","telefone","admin","idEndereco") VALUES (2,'Papelaria Ápice','juridico','senha','09468264870001','contato@empresa.com','3233988734',0,3);
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","identificador","email","telefone","admin","idEndereco") VALUES (3,'Funcionário Exemplo','admin','senha','09876543210','admin@email.com','32956435476',1,2);
INSERT INTO "Cliente" ("id","cartao") VALUES (1,'7846746387576273');
INSERT INTO "Cliente" ("id","cartao") VALUES (2,'8946736409768881');
INSERT INTO "PessoaFisica" ("id","dataNascimento") VALUES (1,'1996-08-17T00:00:00.000000');
INSERT INTO "PessoaJuridica" ("id","razaoSocial") VALUES (2,'Empresa de Papéis LTDA');
INSERT INTO "Funcionario" ("id","matricula","funcao") VALUES (3,'201789034','Caixa');
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (1,'R','Creme de morango',0.32);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (2,'F','Redonda 20cm',0.0625);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (3,'C','Azul',0.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (4,'T','Glacê de limão',0.023);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (5,'R','Ganache meio amargo',0.57);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (2,1,100,150,800);
INSERT INTO "ProdutoPersonalizado_Recheio" ("id","idProdutoPersonalizado","idRecheio") VALUES (1,1,5);
COMMIT;
