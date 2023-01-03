/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Caracteristica;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.ProdutoPersonalizado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author lugar
 */
public class Conexao {

    /**
     * Conecta ao banco de dados confeitaria.db
     *
     * @return o objeto Connection
     */
    static Connection abreConexao() {
        String url = "jdbc:sqlite:confeitaria.db";
        Connection conexao = null;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conexao = DriverManager.getConnection(url, config.toProperties());
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexao;
    }

    static void fechaConexao(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            // Fechamento de conexão falhou
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static int determinaValorErro(String mensagem) {
        if (mensagem.substring(0, 26)
                .equals("[SQLITE_CONSTRAINT_UNIQUE]")) {
            return Util.RETORNO_ERRO_NAO_UNICO;
        } else {
            return Util.RETORNO_ERRO_INDETERMINADO;
        }
    }

    public static void criaBancoDeDados() {
        Connection conn = null;

        try {
            conn = Conexao.abreConexao();
            if (conn == null) {
                return;
            }

            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            stmt.setQueryTimeout(30);

            String sql = "";

            sql = "CREATE TABLE IF NOT EXISTS \"Produto\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"tipo\"	INTEGER NOT NULL DEFAULT 0 CHECK(\"tipo\" IN (0, 1)),"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"ProdutoPronto\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"nome\"	TEXT NOT NULL UNIQUE,\n"
                    + "	\"estoque\"	INTEGER NOT NULL DEFAULT 0,\n"
                    + "	\"valor\"	REAL NOT NULL DEFAULT 0,\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Produto\"(\"id\") ON DELETE CASCADE,\n"
                    + "	PRIMARY KEY(\"id\")\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"ProdutoPersonalizado\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"detalhe\"	TEXT,\n"
                    + "	\"receita\"	TEXT NOT NULL CHECK(receita IN('B','T')),\n"
                    + "	\"idCobertura\"	INTEGER,\n"
                    + "	\"idCor\"	INTEGER NOT NULL,\n"
                    + "	\"idForma\"	INTEGER NOT NULL,\n"
                    + "	PRIMARY KEY(\"id\"),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Produto\"(\"id\") ON DELETE CASCADE\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Transacao\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"valor\"	REAL NOT NULL,\n"
                    + "	\"diaHora\"	TEXT NOT NULL,\n"
                    + "	\"descricao\"	TEXT NOT NULL,\n"
                    + "	\"ehPedido\"	INTEGER NOT NULL DEFAULT 0 CHECK(\"ehPedido\" IN (0, 1)),\n"
                    + "	PRIMARY KEY(\"id\")\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Endereco\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"numero\"	TEXT NOT NULL DEFAULT 'S/N',\n"
                    + "	\"complemento\"	TEXT,\n"
                    + "	\"logradouro\"	TEXT NOT NULL,\n"
                    + "	\"bairro\"	TEXT NOT NULL,\n"
                    + "	\"cidade\"	TEXT NOT NULL,\n"
                    + "	\"uf\"	TEXT NOT NULL,\n"
                    + "	\"cep\"	TEXT NOT NULL,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Usuario\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"nome\"	TEXT NOT NULL,\n"
                    + "	\"nomeUsuario\"	TEXT NOT NULL UNIQUE,\n"
                    + "	\"senhaHash\"	TEXT NOT NULL,\n"
                    + "	\"admin\"	INTEGER NOT NULL DEFAULT 0 CHECK(\"admin\" IN (0, 1)),\n"
                    + "	\"email\"	TEXT NOT NULL UNIQUE,\n"
                    + "	\"telefone\"	TEXT NOT NULL,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Cliente\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"idEndereco\"	INTEGER NOT NULL,\n"
                    + "	\"cartao\"	TEXT NOT NULL CHECK(LENGTH(\"cartao\") == 16),\n"
                    + "	\"identificador\" TEXT NOT NULL,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT),\n"
                    + "	FOREIGN KEY(\"idEndereco\") REFERENCES \"Endereco\"(\"id\") ON DELETE CASCADE\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Funcionario\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"matricula\"	TEXT NOT NULL UNIQUE,\n"
                    + "	\"funcao\"	TEXT NOT NULL,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Usuario\"(\"id\") ON DELETE CASCADE\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Caracteristica\" (\n"
                    + " \"id\"         INTEGER NOT NULL UNIQUE,\n"
                    + " \"tipo\"       TEXT    NOT NULL CHECK (tipo IN (\"F\", \"R\", \"T\", \"C\") ),\n"
                    + " \"nome\"       TEXT    NOT NULL UNIQUE,\n"
                    + " \"valorGrama\" REAL    NOT NULL DEFAULT 0,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Forma\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"recheios\"	INTEGER NOT NULL DEFAULT 0 CHECK(recheios >=0 AND recheios <=3),\n"
                    + "	\"gramaRecheio\"	INTEGER NOT NULL DEFAULT 0,\n"
                    + "	\"gramaCobertura\"	INTEGER NOT NULL DEFAULT 0,\n"
                    + "	\"gramaMassa\"	INTEGER NOT NULL DEFAULT 0,\n"
                    + "	PRIMARY KEY(\"id\"),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Caracteristica\"(\"id\") ON DELETE CASCADE\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"ProdutoPersonalizado_Recheio\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"idProdutoPersonalizado\"	INTEGER NOT NULL,\n"
                    + "	\"idRecheio\"	INTEGER NOT NULL,\n"
                    + "	FOREIGN KEY(\"idRecheio\") REFERENCES \"Caracteristica\"(\"id\") ON DELETE CASCADE,\n"
                    + "	FOREIGN KEY(\"idProdutoPersonalizado\") REFERENCES \"Produto\"(\"id\") ON DELETE CASCADE,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Pedido\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"estado\"	TEXT NOT NULL,\n"
                    + "	\"dataEntrega\"	TEXT NOT NULL,\n"
                    + "	\"comentario\"	TEXT,\n"
                    + "	\"idCliente\"	INTEGER NOT NULL,\n"
                    + "	FOREIGN KEY(\"idCliente\") REFERENCES \"Usuario\"(\"id\"),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Transacao\"(\"id\") ON DELETE CASCADE,\n"
                    + "	PRIMARY KEY(\"id\")\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Item\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"valorTotal\"	REAL NOT NULL,\n"
                    + "	\"quantidade\"	INTEGER NOT NULL,\n"
                    + "	\"idProduto\"	INTEGER NOT NULL,\n"
                    + "	\"idPedido\"	INTEGER NOT NULL,\n"
                    + "	FOREIGN KEY(\"idProduto\") REFERENCES \"Produto\"(\"id\"),\n"
                    + "	FOREIGN KEY(\"idPedido\") REFERENCES \"Pedido\"(\"id\") ON DELETE CASCADE,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            stmt.executeBatch();
            conn.commit();

            sql = "SELECT id FROM Usuario LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            conn.commit();

            if (!rs.next()) {
                sql = "INSERT INTO \"Produto\" (\"id\",\"tipo\") VALUES (1,1);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"tipo\") VALUES (2,1);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"tipo\") VALUES (3,0);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"tipo\") VALUES (4,0);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"tipo\") VALUES (5,0);\n"
                        + "INSERT INTO \"ProdutoPronto\" (\"id\",\"nome\",\"estoque\",\"valor\") VALUES (3,'Brownie',8,6.95);\n"
                        + "INSERT INTO \"ProdutoPronto\" (\"id\",\"nome\",\"estoque\",\"valor\") VALUES (4,'Bolo de milho simples',1,7.89);\n"
                        + "INSERT INTO \"ProdutoPronto\" (\"id\",\"nome\",\"estoque\",\"valor\") VALUES (5,'Sorvete de manga apimentada',0,3.67);\n"
                        + "INSERT INTO \"ProdutoPersonalizado\" (\"id\",\"detalhe\",\"receita\",\"idForma\",\"idCor\",\"idCobertura\") VALUES (1,'Gostoso','B',2,3,4);\n"
                        + "INSERT INTO \"Transacao\" (\"id\",\"valor\",\"diaHora\",\"descricao\") VALUES (0,12.0,'2020-08-17T10:11:16.908732','Transação teste');\n"
                        + "INSERT INTO \"Usuario\" (\"id\",\"nome\",\"nomeUsuario\",\"senhaHash\",\"admin\",\"email\",\"telefone\") VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454');\n"
                        + "INSERT INTO \"Usuario\" (\"id\",\"nome\",\"nomeUsuario\",\"senhaHash\",\"admin\",\"email\",\"telefone\") VALUES (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476');\n"
                        + "INSERT INTO \"Caracteristica\" (\"id\",\"tipo\",\"nome\",\"valorGrama\") VALUES (1,'R','Creme de morango',0.32);\n"
                        + "INSERT INTO \"Caracteristica\" (\"id\",\"tipo\",\"nome\",\"valorGrama\") VALUES (2,'F','Redonda 20cm',0.0625);\n"
                        + "INSERT INTO \"Caracteristica\" (\"id\",\"tipo\",\"nome\",\"valorGrama\") VALUES (3,'C','Azul',0);\n"
                        + "INSERT INTO \"Caracteristica\" (\"id\",\"tipo\",\"nome\",\"valorGrama\") VALUES (4,'T','Glacê de limão',0.023);\n"
                        + "INSERT INTO \"Caracteristica\" (\"id\",\"tipo\",\"nome\",\"valorGrama\") VALUES (5,'R','Ganache meio amargo',0.57);\n"
                        + "INSERT INTO \"Forma\" (\"id\",\"recheios\",\"gramaRecheio\",\"gramaCobertura\",\"gramaMassa\") VALUES (2,1,100,150,800);\n"
                        + "INSERT INTO \"ProdutoPersonalizado_Recheio\" (\"idProdutoPersonalizado\",\"idRecheio\") VALUES (1,5);\n";

                String[] updates = sql.split("\n");
                for (String update : updates) {
                    stmt.addBatch(update);
                }

                stmt.executeBatch();
                conn.commit();
            }

            conn.setAutoCommit(true);

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
    }

    // a partir daqui uma interface para cada subclasse
    public static List<ProdutoPronto> buscaTodosProdutosProntos(boolean ehAdmin) {
        String sql = "SELECT Produto.id, ProdutoPronto.nome, ProdutoPronto.valor,"
                + " ProdutoPronto.estoque FROM Produto"
                + " INNER JOIN ProdutoPronto ON Produto.id = ProdutoPronto.id"
                + " WHERE tipo = 0";
        sql += ehAdmin ? ";" : " AND estoque > 0;";
        Connection conn = null;
        List<ProdutoPronto> listaProdutos = new ArrayList<ProdutoPronto>();
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProdutoPronto produto = new ProdutoPronto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getInt("estoque"));
                listaProdutos.add(produto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
            return listaProdutos;
        }
    }

    public static int insereProdutoPronto(String nome, double valor, int estoque) {
        String sqlProduto = "INSERT INTO Produto(tipo) VALUES(0);";
        String sqlProdutoPronto = "INSERT INTO ProdutoPronto(id, nome, estoque, valor) VALUES(?, ?, ?, ?);";
        Connection conn = null;
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS);
            int linhaInserida = pstmtProduto.executeUpdate();

            // Reverter operação em caso de erro
            if (linhaInserida != 1) {
                conn.rollback();
            } else {
                ResultSet rs = pstmtProduto.getGeneratedKeys();
                if (rs.next()) {
                    idProduto = rs.getInt(1);
                }
                PreparedStatement pstmtProdutoPronto = conn.prepareStatement(sqlProdutoPronto,
                        Statement.RETURN_GENERATED_KEYS);
                pstmtProdutoPronto.setInt(1, idProduto);
                pstmtProdutoPronto.setString(2, nome);
                pstmtProdutoPronto.setInt(3, estoque);
                pstmtProdutoPronto.setDouble(4, valor);
                pstmtProdutoPronto.executeUpdate();
                conn.commit();
            }
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                // Reverter operação em caso de erro
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex2) {
                Logger.getLogger(Conexao.class
                        .getName())
                        .log(Level.SEVERE, null, ex2);
            }
            Logger.getLogger(Conexao.class
                    .getName())
                    .log(Level.SEVERE, null, ex);
            idProduto = determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return idProduto;
        }
    }

    public static int insereProdutoPersonalizado(ProdutoPersonalizado produtoPersonalizado) {
        String sqlBuscaPrincipal = "SELECT DISTINCT Produto.id, Caracteristica.nome as recheio\n"
                + "FROM Produto\n"
                + "INNER JOIN ProdutoPersonalizado ON Produto.id = ProdutoPersonalizado.id\n"
                + "INNER JOIN ProdutoPersonalizado_Recheio ON ProdutoPersonalizado_Recheio.idProdutoPersonalizado = ProdutoPersonalizado.id\n"
                + "INNER JOIN Caracteristica\n"
                + "ON  Caracteristica.id = ProdutoPersonalizado_Recheio.idRecheio\n"
                + "AND ProdutoPersonalizado.idCobertura = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"T\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "AND ProdutoPersonalizado.idCor = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"C\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "AND ProdutoPersonalizado.idForma = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"F\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "WHERE detalhe= ? AND Caracteristica.tipo=\"R\" ORDER BY Produto.id;";
        Connection conn = null;
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        try {

            double valorCalculado = 100;
            // Se já existe, retorna ID. Senão, cria novo e retorna ID
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);
            PreparedStatement pstmtBuscaPrincipal = conn.prepareStatement(sqlBuscaPrincipal);
            pstmtBuscaPrincipal.setString(1, produtoPersonalizado.getCobertura().getNome());
            pstmtBuscaPrincipal.setString(2, produtoPersonalizado.getCor().getNome());
            pstmtBuscaPrincipal.setString(3, produtoPersonalizado.getForma().getNome());
            pstmtBuscaPrincipal.setString(4, produtoPersonalizado.getDetalhe());
            ResultSet rsBuscaPrincipal = pstmtBuscaPrincipal.executeQuery();

            boolean mudanca = false;
            int tamanhoRecheiosPreenchidos = 0;
            List<Caracteristica> recheiosPreenchidos = null;
            recheiosPreenchidos = produtoPersonalizado.getRecheios();
            tamanhoRecheiosPreenchidos = recheiosPreenchidos.size();

            if (rsBuscaPrincipal.next()) {
                idProduto = rsBuscaPrincipal.getInt("id");
                boolean[] recheiosBatem = new boolean[tamanhoRecheiosPreenchidos];
                int contador = 0;
                do {
                    for (int i = 0; i < tamanhoRecheiosPreenchidos; i++) {
                        String recheioBanco = rsBuscaPrincipal.getString("recheio");
                        if (recheioBanco.equals(recheiosPreenchidos.get(i).getNome())) {
                            recheiosBatem[i] = true;
                        }
                    }
                    contador++;
                } while (rsBuscaPrincipal.next());
                if (contador == tamanhoRecheiosPreenchidos) {
                    for (boolean b : recheiosBatem) {
                        if (!b) {
                            mudanca = true;
                        }
                    }
                } else {
                    mudanca = true;
                }
            } else {
                mudanca = true;
            }

            if (!mudanca) {
                return idProduto;
            } else {
                String sqlProduto = "INSERT INTO Produto(tipo) VALUES(1);";
                String sqlProdutoPersonalizado = "INSERT INTO ProdutoPersonalizado(id, detalhe, receita, idCobertura, idCor, idForma) VALUES(?, ?, ?, ?, ?, ?);";

                PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS);
                int linhaInserida = pstmtProduto.executeUpdate();

                // Reverter operação em caso de erro
                if (linhaInserida != 1) {
                    conn.rollback();
                } else {
                    ResultSet rs = pstmtProduto.getGeneratedKeys();
                    if (rs.next()) {
                        idProduto = rs.getInt(1);
                    }

                    PreparedStatement pstmtProdutoPersonalizado = conn.prepareStatement(sqlProdutoPersonalizado,
                            Statement.RETURN_GENERATED_KEYS);
                    pstmtProdutoPersonalizado.setInt(1, idProduto);
                    pstmtProdutoPersonalizado.setString(2, produtoPersonalizado.getDetalhe());
                    pstmtProdutoPersonalizado.setString(3, produtoPersonalizado.getReceita());
                    pstmtProdutoPersonalizado.setInt(4, produtoPersonalizado.getCobertura().getId());
                    pstmtProdutoPersonalizado.setInt(5, produtoPersonalizado.getCor().getId());
                    pstmtProdutoPersonalizado.setInt(6, produtoPersonalizado.getForma().getId());
                    linhaInserida = pstmtProdutoPersonalizado.executeUpdate();

                    // Reverter operação em caso de erro
                    if (linhaInserida != 1) {
                        conn.rollback();
                    } else {
                        String sqlRecheio = "INSERT INTO ProdutoPersonalizado_Recheio(idProdutoPersonalizado, idRecheio) VALUES";
                        for (int i = 0; i < tamanhoRecheiosPreenchidos; i++) {
                            sqlRecheio = sqlRecheio.concat("(" + idProduto + ",  ?), ");
                        }
                        sqlRecheio = sqlRecheio.substring(0, sqlRecheio.length() - 2).concat(";");

                        PreparedStatement pstmtRecheio = conn.prepareStatement(sqlRecheio);
                        int i = 1;
                        for (Caracteristica recheio : recheiosPreenchidos) {
                            pstmtRecheio.setInt(i, recheio.getId());
                            i++;
                        }

                        pstmtRecheio.executeUpdate();
                        conn.commit();
                    }
                }
            }
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                // Reverter operação em caso de erro
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex2) {
                Logger.getLogger(Conexao.class
                        .getName())
                        .log(Level.SEVERE, null, ex2);
            }
            Logger.getLogger(Conexao.class
                    .getName())
                    .log(Level.SEVERE, null, ex);
            idProduto = determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return idProduto;
        }
    }

    public static int atualizaProdutoPronto(ProdutoPronto produto) {

        String sqlProdutoPronto = "UPDATE ProdutoPronto SET nome = ?, estoque = ?, valor = ? WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtProdutoPronto = conn.prepareStatement(sqlProdutoPronto);
            pstmtProdutoPronto.setString(1, produto.getNome());
            pstmtProdutoPronto.setInt(2, produto.getEstoque());
            pstmtProdutoPronto.setDouble(3, produto.getValor());
            pstmtProdutoPronto.setInt(4, produto.getId());
            int linhaAtualizada = pstmtProdutoPronto.executeUpdate();

            // Reverter operação em caso de erro
            if (linhaAtualizada != 1) {
                conn.rollback();
            } else {
                conn.commit();
            }
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
        }

        return valorRetorno;

    }

    public static int atualizaEstoqueProdutoPronto(int id, int estoque) {
        String sql = "UPDATE ProdutoPronto SET estoque = ? WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, estoque);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return valorRetorno;
        }
    }

    public static int deletaProduto(int idProduto) {
        String sql = "DELETE FROM Produto WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idProduto);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
        }
        return valorRetorno;
    }

}
