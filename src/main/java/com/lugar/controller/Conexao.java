/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Cliente;
import com.lugar.model.Usuario;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.Transacao;
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
import java.time.LocalDateTime;
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
    private static Connection abreConexao() {
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

    private static void fechaConexao(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            // Fechamento de conexão falhou
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int determinaValorErro(String mensagem) {
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
                    + "	\"valor\"	REAL NOT NULL DEFAULT 0,\n"
                    + "	\"tipo\"	INTEGER NOT NULL DEFAULT 0 CHECK(\"tipo\" IN (0, 1)),"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"ProdutoPronto\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"nome\"	TEXT NOT NULL UNIQUE,\n"
                    + "	\"estoque\"	INTEGER NOT NULL DEFAULT 0,\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Produto\"(\"id\") ON DELETE CASCADE,\n"
                    + "	PRIMARY KEY(\"id\")\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"ProdutoPersonalizado\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"recheio\"	TEXT NOT NULL,\n"
                    + "	\"cobertura\"	TEXT NOT NULL,\n"
                    + "	\"detalhe\"	TEXT,\n"
                    + "	PRIMARY KEY(\"id\"),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Produto\"(\"id\") ON DELETE CASCADE\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Transacao\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"valor\"	REAL NOT NULL,\n"
                    + "	\"diaHora\"	TEXT NOT NULL,\n"
                    + "	\"descricao\"	TEXT NOT NULL,\n"
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
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Endereco\"(\"id\") ON DELETE CASCADE\n"
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
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT),\n"
                    + "	FOREIGN KEY(\"idEndereco\") REFERENCES \"Endereco\"(\"id\")\n"
                    + ");";
            stmt.addBatch(sql);

            sql = "CREATE TABLE IF NOT EXISTS \"Funcionario\" (\n"
                    + "	\"id\"	INTEGER NOT NULL UNIQUE,\n"
                    + "	\"matricula\"	TEXT NOT NULL UNIQUE,\n"
                    + "	\"funcao\"	TEXT NOT NULL,\n"
                    + "	PRIMARY KEY(\"id\" AUTOINCREMENT),\n"
                    + "	FOREIGN KEY(\"id\") REFERENCES \"Usuario\"(\"id\")\n"
                    + ");";
            stmt.addBatch(sql);

            stmt.executeBatch();
            conn.commit();

            sql = "SELECT id FROM Usuario LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            conn.commit();

            if (!rs.next()) {
                sql = "INSERT INTO \"Transacao\" VALUES (0,12.0,'2020-08-17T10:11:16.908732','teste teste');\n"
                        + "INSERT INTO \"Usuario\" VALUES (5,'Cliente Exemplo','cliente','senha',0,'cliente@email.com','32980675454');\n"
                        + "INSERT INTO \"Usuario\" VALUES (6,'Funcionário Exemplo','admin','senha',1,'admin@email.com','32956435476');\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"valor\",\"tipo\") VALUES (1,100.0,1);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"valor\",\"tipo\") VALUES (2,100.0,1);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"valor\",\"tipo\") VALUES (3,5.56,0);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"valor\",\"tipo\") VALUES (4,7.897,0);\n"
                        + "INSERT INTO \"Produto\" (\"id\",\"valor\",\"tipo\") VALUES (5,3.67,0);\n"
                        + "INSERT INTO \"ProdutoPersonalizado\" (\"id\",\"recheio\",\"cobertura\",\"detalhe\") VALUES (1,'Chocolate meio amargo','Glacê de limão','');\n"
                        + "INSERT INTO \"ProdutoPersonalizado\" (\"id\",\"recheio\",\"cobertura\",\"detalhe\") VALUES (2,'Chocolate meio amargo','Chantilly','dsdas');\n"
                        + "INSERT INTO \"ProdutoPronto\" (\"id\",\"nome\",\"estoque\") VALUES (3,'Brownie',8);\n"
                        + "INSERT INTO \"ProdutoPronto\" (\"id\",\"nome\",\"estoque\") VALUES (4,'Bolo de milho simples',1);\n"
                        + "INSERT INTO \"ProdutoPronto\" (\"id\",\"nome\",\"estoque\") VALUES (5,'Sorvete de manga apimentada',0);";

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

    public static List<Usuario> buscaTodosUsuariosLogin() {
        Connection conn = null;
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        try {
            conn = Conexao.abreConexao();
            if (conn == null) {
                return listaUsuarios;
            }
            String sql = "SELECT id, nomeUsuario, senhaHash, admin FROM Usuario;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nomeUsuario"),
                        rs.getString("senhaHash"),
                        rs.getInt("admin") == 1
                );
                listaUsuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
            return listaUsuarios;
        }
    }

    public static int insereUsuario(String nome, String nomeUsuario, String senhaHash) {
        String sql = "INSERT INTO Usuario(nome, nomeUsuario, senhaHash, admin) VALUES(?, ?, ?, ?);";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, nomeUsuario);
            pstmt.setString(3, senhaHash);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return valorRetorno;
        }
    }

    public static int insereCliente(Cliente cliente) {
        String sql = "INSERT INTO Usuario(nome, nomeUsuario, senhaHash, admin, email, telefone, endereco, cartao, identificador) VALUES(?, ?, ?, 0, ?, ?, ?, ?, ?);";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getNomeUsuario());
            pstmt.setString(3, cliente.getSenhaHash());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getTelefone());
            pstmt.setString(6, cliente.getEndereco());
            pstmt.setString(7, cliente.getCartao());
            pstmt.setString(8, cliente.getIdentificador());

            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return valorRetorno;
        }
    }

    public static int insereTransacao(Transacao transacao) {
        String sql = "INSERT INTO Transacao(valor, diaHora,descricao) VALUES(?,?,?);";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, transacao.getValor());
            pstmt.setString(2, transacao.getDiaHoraString());
            pstmt.setString(3, transacao.getDescricao());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return valorRetorno;
        }
    }

    public static Produto buscaProduto(int id) {
        String sql = "SELECT Produto.tipo, Produto.valor, "
                + "ProdutoPronto.estoque, ProdutoPronto.nome, "
                + "ProdutoPersonalizado.recheio, "
                + "ProdutoPersonalizado.cobertura, ProdutoPersonalizado.detalhe "
                + "FROM Produto "
                + "LEFT JOIN ProdutoPersonalizado ON Produto.id = ProdutoPersonalizado.id "
                + "LEFT JOIN ProdutoPronto ON Produto.id = ProdutoPronto.id "
                + "WHERE Produto.id=" + id + ";";
        Connection conn = null;
        Produto produto = null;

        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getInt("tipo") == Util.TIPO_PRONTO) {
                    produto = new ProdutoPronto(
                            id,
                            rs.getString("nome"),
                            rs.getDouble("valor"),
                            rs.getInt("estoque")
                    );
                } else {
                    produto = new ProdutoPersonalizado(
                            id,
                            rs.getDouble("valor"),
                            rs.getString("recheio"),
                            rs.getString("cobertura"),
                            rs.getString("detalhe")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
            return produto;
        }
    }

    public static ProdutoPronto buscaProdutoPronto(int id) {
        String sql = "SELECT ProdutoPronto.nome, Produto.valor, ProdutoPronto.estoque"
                + " FROM Produto INNER JOIN ProdutoPronto"
                + " ON Produto.id = ProdutoPronto.id"
                + " WHERE Produto.id=" + id + ";";
        Connection conn = null;
        ProdutoPronto produto = null;
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                produto = new ProdutoPronto(
                        id,
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getInt("estoque")
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
            return produto;
        }
    }

    public static int buscaEstoqueProduto(int id) {
        String sql = "SELECT estoque FROM ProdutoPronto WHERE id=" + id + ";";
        Connection conn = null;
        int estoque = 0;
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                estoque = rs.getInt("estoque");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
            return estoque;
        }
    }

    public static List<ProdutoPronto> buscaTodosProdutosProntos(boolean ehAdmin) {
        String sql = "SELECT Produto.id, ProdutoPronto.nome, Produto.valor,"
                + " ProdutoPronto.estoque FROM Produto"
                + " INNER JOIN ProdutoPronto ON Produto.id = ProdutoPronto.id";
        sql += ehAdmin ? ";" : " WHERE estoque > 0;";
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
                        rs.getInt("estoque")
                );
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
        String sqlProduto = "INSERT INTO Produto(valor) VALUES(?);";
        String sqlProdutoPronto = "INSERT INTO ProdutoPronto(id, nome, estoque) VALUES(?, ?, ?);";
        Connection conn = null;
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS);
            pstmtProduto.setDouble(1, valor);
            int linhaInserida = pstmtProduto.executeUpdate();

            // Reverter operação em caso de erro
            if (linhaInserida != 1) {
                conn.rollback();
            } else {
                ResultSet rs = pstmtProduto.getGeneratedKeys();
                if (rs.next()) {
                    idProduto = rs.getInt(1);
                }
                PreparedStatement pstmtProdutoPronto
                        = conn.prepareStatement(sqlProdutoPronto,
                                Statement.RETURN_GENERATED_KEYS);
                pstmtProdutoPronto.setInt(1, idProduto);
                pstmtProdutoPronto.setString(2, nome);
                pstmtProdutoPronto.setInt(3, estoque);
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
        String sqlBusca = "SELECT Produto.id FROM Produto INNER JOIN ProdutoPersonalizado "
                + "ON Produto.id = ProdutoPersonalizado.id WHERE valor = ? AND tipo = 1 "
                + "AND recheio = ? AND cobertura = ? AND detalhe = ?;";
        Connection conn = null;
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        try {
            double valorCalculado = 100;
            // Se já existe, retorna ID. Senão, cria novo e retorna ID
            conn = Conexao.abreConexao();
            PreparedStatement pstmtBusca = conn.prepareStatement(sqlBusca);
            pstmtBusca.setDouble(1, valorCalculado);
            pstmtBusca.setString(2, produtoPersonalizado.getRecheio());
            pstmtBusca.setString(3, produtoPersonalizado.getCobertura());
            pstmtBusca.setString(4, produtoPersonalizado.getDetalhe());
            ResultSet rsBusca = pstmtBusca.executeQuery();

            if (rsBusca.next()) {
                idProduto = rsBusca.getInt("id");
            } else {
                String sqlProduto = "INSERT INTO Produto(valor, tipo) VALUES(?, 1);";
                String sqlProdutoPersonalizado = "INSERT INTO ProdutoPersonalizado(id, recheio, cobertura, detalhe) VALUES(?, ?, ?, ?);";

                conn.setAutoCommit(false);

                PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS);
                pstmtProduto.setDouble(1, valorCalculado);
                int linhaInserida = pstmtProduto.executeUpdate();

                // Reverter operação em caso de erro
                if (linhaInserida != 1) {
                    conn.rollback();
                } else {
                    ResultSet rs = pstmtProduto.getGeneratedKeys();
                    if (rs.next()) {
                        idProduto = rs.getInt(1);
                    }

                    PreparedStatement pstmtProdutoPersonalizado
                            = conn.prepareStatement(sqlProdutoPersonalizado,
                                    Statement.RETURN_GENERATED_KEYS);
                    pstmtProdutoPersonalizado.setInt(1, idProduto);
                    pstmtProdutoPersonalizado.setString(2, produtoPersonalizado.getRecheio());
                    pstmtProdutoPersonalizado.setString(3, produtoPersonalizado.getCobertura());
                    pstmtProdutoPersonalizado.setString(4, produtoPersonalizado.getDetalhe());
                    pstmtProdutoPersonalizado.executeUpdate();
                    conn.commit();
                }
                conn.setAutoCommit(true);
            }
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
        String sqlProduto = "UPDATE Produto SET valor = ? WHERE id = ?;";
        String sqlProdutoPronto = "UPDATE ProdutoPronto SET nome = ?, estoque = ? WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto);
            pstmtProduto.setDouble(1, produto.getValor());
            pstmtProduto.setInt(2, produto.getId());
            int linhaAtualizada = pstmtProduto.executeUpdate();

            // Reverter operação em caso de erro
            if (linhaAtualizada != 1) {
                conn.rollback();
            } else {
                PreparedStatement pstmtProdutoPronto = conn.prepareStatement(sqlProdutoPronto);
                pstmtProdutoPronto.setString(1, produto.getNome());
                pstmtProdutoPronto.setInt(2, produto.getEstoque());
                pstmtProdutoPronto.setInt(3, produto.getId());
                pstmtProdutoPronto.executeUpdate();
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
            return valorRetorno;
        }
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
            return valorRetorno;
        }
    }

    public static int deletaTransacao(int idTransacao) {
        String sql = "DELETE FROM Transacao WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
//        try{
//            conn = Conexao.abreConexao();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, idTransacao);
//            pstmt.executeUpdate();
//        }
        return 0;
    }

    public static List<Transacao> buscaTodasTransacoes() {
        String sql = "SELECT id, valor, diaHora, descricao FROM Transacao;";
        Connection conn = null;
        List<Transacao> listaTransacoes = new ArrayList<Transacao>();
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("diaHora"));
                Transacao transacao = new Transacao(
                        rs.getInt("id"),
                        rs.getDouble("valor"),
                        dataHora,
                        rs.getString("descricao")
                );
                listaTransacoes.add(transacao);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
            return listaTransacoes;
        }

    }
}
