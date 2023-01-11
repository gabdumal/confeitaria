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
	"receita"	TEXT NOT NULL CHECK("receita" IN ('B', 'T')),
	"idCor"	INTEGER NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Produto"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Bolo" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idForma"	INTEGER NOT NULL,
	"idCobertura"	INTEGER NOT NULL,
	FOREIGN KEY("idCobertura") REFERENCES "Caracteristica"("id"),
	PRIMARY KEY("id"),
	FOREIGN KEY("idForma") REFERENCES "Forma"("id"),
	FOREIGN KEY("id") REFERENCES "ProdutoPersonalizado"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Trufa" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idRecheio"	INTEGER NOT NULL,
	FOREIGN KEY("id") REFERENCES "ProdutoPersonalizado"("id") ON DELETE CASCADE,
	FOREIGN KEY("idRecheio") REFERENCES "Caracteristica"("id"),
	PRIMARY KEY("id")
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
	"cep"	TEXT NOT NULL CHECK(LENGTH("cep") == 8),
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
	FOREIGN KEY("idEndereco") REFERENCES "Endereco"("id") ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Cliente" (
	"id"	INTEGER NOT NULL UNIQUE,
	"cartao"	TEXT NOT NULL CHECK(LENGTH("cartao") == 16),
	"fisica"	INTEGER NOT NULL DEFAULT 1 CHECK("fisica" IN (0, 1)),
	FOREIGN KEY("id") REFERENCES "Usuario"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
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
	FOREIGN KEY("id") REFERENCES "Cliente"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
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
	"nome"	TEXT NOT NULL,
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
CREATE TABLE IF NOT EXISTS "Bolo_Recheio" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idBolo"	INTEGER NOT NULL,
	"idRecheio"	INTEGER NOT NULL,
	FOREIGN KEY("idBolo") REFERENCES "Bolo"("id") ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("idRecheio") REFERENCES "Caracteristica"("id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Pedido" (
	"id"	INTEGER NOT NULL UNIQUE,
	"estado"	TEXT NOT NULL,
	"dataEntrega"	TEXT NOT NULL,
	"comentario"	TEXT,
	"idCliente"	INTEGER NOT NULL,
	FOREIGN KEY("id") REFERENCES "Transacao"("id") ON DELETE CASCADE,
	FOREIGN KEY("idCliente") REFERENCES "Usuario"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Item" (
	"id"	INTEGER NOT NULL UNIQUE,
	"valorTotal"	REAL NOT NULL,
	"quantidade"	INTEGER NOT NULL,
	"idProduto"	INTEGER NOT NULL,
	"idPedido"	INTEGER NOT NULL,
	FOREIGN KEY("idPedido") REFERENCES "Pedido"("id") ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("idProduto") REFERENCES "Produto"("id")
);
INSERT INTO "Produto" ("id","tipo") VALUES (1,0);
INSERT INTO "Produto" ("id","tipo") VALUES (2,0);
INSERT INTO "Produto" ("id","tipo") VALUES (4,0);
INSERT INTO "Produto" ("id","tipo") VALUES (5,0);
INSERT INTO "Produto" ("id","tipo") VALUES (6,0);
INSERT INTO "Produto" ("id","tipo") VALUES (7,0);
INSERT INTO "Produto" ("id","tipo") VALUES (8,0);
INSERT INTO "Produto" ("id","tipo") VALUES (9,0);
INSERT INTO "Produto" ("id","tipo") VALUES (10,0);
INSERT INTO "Produto" ("id","tipo") VALUES (11,0);
INSERT INTO "Produto" ("id","tipo") VALUES (12,0);
INSERT INTO "Produto" ("id","tipo") VALUES (13,0);
INSERT INTO "Produto" ("id","tipo") VALUES (14,0);
INSERT INTO "Produto" ("id","tipo") VALUES (15,0);
INSERT INTO "Produto" ("id","tipo") VALUES (16,0);
INSERT INTO "Produto" ("id","tipo") VALUES (17,0);
INSERT INTO "Produto" ("id","tipo") VALUES (18,0);
INSERT INTO "Produto" ("id","tipo") VALUES (19,0);
INSERT INTO "Produto" ("id","tipo") VALUES (20,0);
INSERT INTO "Produto" ("id","tipo") VALUES (21,0);
INSERT INTO "Produto" ("id","tipo") VALUES (22,0);
INSERT INTO "Produto" ("id","tipo") VALUES (23,0);
INSERT INTO "Produto" ("id","tipo") VALUES (24,0);
INSERT INTO "Produto" ("id","tipo") VALUES (25,0);
INSERT INTO "Produto" ("id","tipo") VALUES (26,0);
INSERT INTO "Produto" ("id","tipo") VALUES (27,0);
INSERT INTO "Produto" ("id","tipo") VALUES (28,0);
INSERT INTO "Produto" ("id","tipo") VALUES (29,0);
INSERT INTO "Produto" ("id","tipo") VALUES (30,0);
INSERT INTO "Produto" ("id","tipo") VALUES (31,0);
INSERT INTO "Produto" ("id","tipo") VALUES (32,0);
INSERT INTO "Produto" ("id","tipo") VALUES (33,0);
INSERT INTO "Produto" ("id","tipo") VALUES (34,0);
INSERT INTO "Produto" ("id","tipo") VALUES (35,0);
INSERT INTO "Produto" ("id","tipo") VALUES (36,0);
INSERT INTO "Produto" ("id","tipo") VALUES (37,0);
INSERT INTO "Produto" ("id","tipo") VALUES (38,0);
INSERT INTO "Produto" ("id","tipo") VALUES (39,0);
INSERT INTO "Produto" ("id","tipo") VALUES (40,0);
INSERT INTO "Produto" ("id","tipo") VALUES (41,0);
INSERT INTO "Produto" ("id","tipo") VALUES (42,0);
INSERT INTO "Produto" ("id","tipo") VALUES (43,0);
INSERT INTO "Produto" ("id","tipo") VALUES (44,0);
INSERT INTO "Produto" ("id","tipo") VALUES (45,0);
INSERT INTO "Produto" ("id","tipo") VALUES (46,0);
INSERT INTO "Produto" ("id","tipo") VALUES (47,0);
INSERT INTO "Produto" ("id","tipo") VALUES (48,0);
INSERT INTO "Produto" ("id","tipo") VALUES (49,0);
INSERT INTO "Produto" ("id","tipo") VALUES (50,0);
INSERT INTO "Produto" ("id","tipo") VALUES (51,0);
INSERT INTO "Produto" ("id","tipo") VALUES (52,1);
INSERT INTO "Produto" ("id","tipo") VALUES (53,1);
INSERT INTO "Produto" ("id","tipo") VALUES (54,1);
INSERT INTO "Produto" ("id","tipo") VALUES (55,1);
INSERT INTO "Produto" ("id","tipo") VALUES (56,1);
INSERT INTO "Produto" ("id","tipo") VALUES (57,1);
INSERT INTO "Produto" ("id","tipo") VALUES (58,1);
INSERT INTO "Produto" ("id","tipo") VALUES (59,1);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (1,'Brownie',1,6.95);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (2,'Bolo de milho simples',5,35.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (4,'Bolo de maracujá',8,35.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (5,'Bolo de limão',4,35.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (6,'Bolo de chocolate',9,40.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (7,'Brigadeirão',43,3.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (8,'Bolo de abacaxi',4,30.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (9,'Bolo de cenoura',1,35.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (10,'Pudim de leite condensado',10,30.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (11,'Beijinho de leite ninho',60,1.5);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (12,'Brigadeiro Gourmet de Leite Ninho C/ Avelã',31,5.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (13,'Brigadeiro Gourmet de Pistache',28,5.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (14,'Brigadeiro Gourmet de Café',16,5.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (15,'Bombom de Brigadeiro com Cereja',18,4.98);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (16,'Brigadeiro Gourmet Ao Leite',17,4.98);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (17,'Bombom de limão',96,4.98);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (18,'Bem casados',95,1.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (19,'Cajuzinho',100,0.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (20,'Bombom de Coco',96,4.98);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (21,'Brigadeiro pequeno',150,1.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (22,'Torta de Palmito',6,46.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (23,'Torta de Camarão',8,48.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (24,'Mini Coxinha de Frango',220,0.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (25,'Empada de Frango',18,4.49);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (26,'Mini coxinha de Franco c/ Requeijão',140,1.49);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (27,'Mini bolinha de queijo',245,0.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (28,'Pastel de Vento',34,4.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (29,'Pastel de frango',36,9.97);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (30,'Pastel de frango c/ catupiry',40,14.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (31,'Pastel de palmito',30,7.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (32,'Pão de queijo',95,2.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (33,'Mini kibe recheado',100,1.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (34,'Pastel de queijo',27,9.89);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (35,'Pastel de bacalhau',20,9.98);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (36,'Esfiha de Carne',40,5.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (37,'Empada de Camarão',15,6.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (38,'Bala de Coco c/ frutas Vermelhas',50,1.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (39,'Bala de Coco c/ Paçoca',120,1.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (40,'Bala de Coco c/ negresco',120,2.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (41,'Bala de Coco banhada no chocolate Ao leite',30,2.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (42,'Mini churros',270,1.49);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (43,'Bala de coco tradicional',900,2.99);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (44,'Bolo Red VelVet',20,99.9);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (45,'Bolo Vulcao c/ leite ninho',0,90.9);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (46,'Bolo de Banana',0,35.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (47,'Bolo de abacaxi c/ leite condensado',0,59.9);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (48,'Bolo de chocolate branco',0,39.9);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (49,'Bolo de Ameixa',0,49.9);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (50,'Bolo formigueiro',0,35.0);
INSERT INTO "ProdutoPronto" ("id","nome","estoque","valor") VALUES (51,'Bolo de morango',0,35.0);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (52,'Quero todas bem enbrulhadas pois é para presente s2','T',7);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (53,'Quero todas bem enbrulhadas pois é para presente s2 s2','T',7);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (54,'Já está muito caro, por favor caprichem!','B',16);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (55,'Caprichem pfv!','B',9);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (56,'','B',8);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (57,'','T',7);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (58,'Todas embrulhadas em amarelo!','T',8);
INSERT INTO "ProdutoPersonalizado" ("id","detalhe","receita","idCor") VALUES (59,'Se puderem jogar granulado por cima eu ficaria mto grato! s2','B',7);
INSERT INTO "Bolo" ("id","idForma","idCobertura") VALUES (54,6,22);
INSERT INTO "Bolo" ("id","idForma","idCobertura") VALUES (55,6,19);
INSERT INTO "Bolo" ("id","idForma","idCobertura") VALUES (56,2,19);
INSERT INTO "Bolo" ("id","idForma","idCobertura") VALUES (59,1,20);
INSERT INTO "Trufa" ("id","idRecheio") VALUES (52,28);
INSERT INTO "Trufa" ("id","idRecheio") VALUES (53,28);
INSERT INTO "Trufa" ("id","idRecheio") VALUES (57,23);
INSERT INTO "Trufa" ("id","idRecheio") VALUES (58,24);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (1,12.1,'2020-08-17T10:11:16.908732','Compra de materiais',0);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (2,103.04,'2023-01-11T01:09:45.722742330','Pedido',1);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (3,330.566,'2023-01-11T01:20:15.721144586','Pedido',1);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (4,93.9,'2023-01-11T01:34:03.480132244','Pedido',1);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (5,218.15,'2023-01-11T01:35:01.503462937','Pedido',1);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (6,263.745,'2023-01-11T01:36:56.329423447','Pedido',1);
INSERT INTO "Transacao" ("id","valor","diaHora","descricao","ehPedido") VALUES (7,863.015,'2023-01-11T01:40:04.931118685','Pedido',1);
INSERT INTO "Endereco" ("id","numero","complemento","logradouro","bairro","cidade","uf","cep") VALUES (1,'25','Ao lado do mercado','Rua dos bobos','Vale Místico','Lírica do Norte','MG','37589287');
INSERT INTO "Endereco" ("id","numero","complemento","logradouro","bairro","cidade","uf","cep") VALUES (2,'34','APT 506','Avenida Brasil','Centro','Mar de Espanha','MG','38976235');
INSERT INTO "Endereco" ("id","numero","complemento","logradouro","bairro","cidade","uf","cep") VALUES (3,'S/N','','Rua das Indústrias','Distrito Industrial','Juiz de Fora','MG','38079725');
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","identificador","email","telefone","admin","idEndereco") VALUES (1,'Cliente PF Exemplo','cliente','senha','01234567890','cliente@email.com','32980675454',0,1);
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","identificador","email","telefone","admin","idEndereco") VALUES (2,'Papelaria O Escritório','juridico','senha','09468264870001','contato@papelaria.com','3233988734',0,3);
INSERT INTO "Usuario" ("id","nome","nomeUsuario","senhaHash","identificador","email","telefone","admin","idEndereco") VALUES (3,'Funcionário Exemplo','admin','senha','09876543210','admin@email.com','32956435476',1,2);
INSERT INTO "Cliente" ("id","cartao","fisica") VALUES (1,'7846746387576273',1);
INSERT INTO "Cliente" ("id","cartao","fisica") VALUES (2,'8946736409768881',0);
INSERT INTO "PessoaFisica" ("id","dataNascimento") VALUES (1,'1996-08-17');
INSERT INTO "PessoaJuridica" ("id","razaoSocial") VALUES (2,'Empresa de Papéis LTDA');
INSERT INTO "Funcionario" ("id","matricula","funcao") VALUES (3,'201709675','Caixa');
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (1,'F','Redonda 20cm',0.0361);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (2,'F','Redonda 25cm',0.03);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (3,'F','Redonda 30cm',0.0315);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (4,'F','Quadrada 20cm',0.0377);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (5,'F','Quadrada 25cm',0.0347);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (6,'F','Quadrada 30cm',0.0345);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (7,'C','Sem cor',0.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (8,'C','Amarelo',1.5);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (9,'C','Azul',2.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (10,'C','Verde claro',2.5);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (11,'C','Rosa',3.5);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (12,'C','Verde escuro',4.75);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (13,'C','Vermelho',5.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (14,'C','Azul escuro',7.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (15,'C','Roxo',8.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (16,'C','Preto',10.0);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (17,'T','Glacê branco',0.028);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (18,'T','Chantininho',0.034);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (19,'T','Chantilly',0.04);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (20,'T','Brigadeiro',0.045);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (21,'T','Ganache ao leite',0.072);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (22,'T','Ganache meio amargo',0.079);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (23,'R','Creme branco',0.049);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (24,'R','Prestígio',0.052);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (25,'R','Ameixa',0.054);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (26,'R','Creme de morango',0.059);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (27,'R','Doce de leite',0.061);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (28,'R','Brigadeiro',0.067);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (29,'R','Ganache ao leite',0.072);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (30,'R','Ganache meio amargo',0.079);
INSERT INTO "Caracteristica" ("id","tipo","nome","valorGrama") VALUES (31,'R','Chocolate com avelãs',0.082);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (1,1,800,700,850);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (2,2,700,1400,1200);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (3,3,700,1700,1750);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (4,1,800,1100,1150);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (5,2,800,1600,1700);
INSERT INTO "Forma" ("id","recheios","gramaRecheio","gramaCobertura","gramaMassa") VALUES (6,3,817,2100,2400);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (1,54,31);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (2,54,29);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (3,54,30);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (4,55,31);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (5,55,28);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (6,55,23);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (7,56,24);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (8,56,25);
INSERT INTO "Bolo_Recheio" ("id","idBolo","idRecheio") VALUES (9,59,28);
INSERT INTO "Pedido" ("id","estado","dataEntrega","comentario","idCliente") VALUES (2,'E','2023-01-20T14:00','Entregar no apt 301, fica ao lado do meu!',1);
INSERT INTO "Pedido" ("id","estado","dataEntrega","comentario","idCliente") VALUES (3,'E','2023-04-14T14:30','Estarei em casa a partir das duas!',1);
INSERT INTO "Pedido" ("id","estado","dataEntrega","comentario","idCliente") VALUES (4,'F','2023-07-14T11:58','',1);
INSERT INTO "Pedido" ("id","estado","dataEntrega","comentario","idCliente") VALUES (5,'S','2023-01-13T20:00','',1);
INSERT INTO "Pedido" ("id","estado","dataEntrega","comentario","idCliente") VALUES (6,'S','2023-03-12T20:30','Entregar na portaria!',2);
INSERT INTO "Pedido" ("id","estado","dataEntrega","comentario","idCliente") VALUES (7,'S','2023-02-15T19:25','Caso eu não esteja em casa, favor entregar no apt 210!',2);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (1,13.4,5,53,2);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (2,40.0,1,6,2);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (3,29.7,30,24,2);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (4,19.94,2,29,2);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (5,330.566,1,55,3);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (6,13.9,2,1,4);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (7,80.0,2,6,4);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (8,29.67,3,34,5);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (9,9.95,5,18,5);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (10,167.7,1,56,5);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (11,5.88,3,57,5);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (12,4.95,5,27,5);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (13,19.92,4,20,6);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (14,6.0,2,7,6);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (15,105.0,3,9,6);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (16,105.875,50,58,6);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (17,14.97,3,28,6);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (18,11.98,2,13,6);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (19,14.95,5,32,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (20,19.92,4,17,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (21,140.0,4,5,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (22,15.0,5,7,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (23,30.0,1,8,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (24,140.0,4,9,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (25,14.9,10,26,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (26,299.0,100,43,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (27,49.5,50,27,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (28,115.785,1,59,7);
INSERT INTO "Item" ("id","valorTotal","quantidade","idProduto","idPedido") VALUES (29,23.96,4,12,7);
COMMIT;
