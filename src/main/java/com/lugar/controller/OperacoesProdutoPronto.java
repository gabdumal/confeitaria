/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import static com.lugar.controller.Conexao.determinaValorErro;
import com.lugar.model.ProdutoPronto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public class OperacoesProdutoPronto implements OperacoesConexao<ProdutoPronto> {

    @Override
    public List<ProdutoPronto> buscaTodos() {
        return this.buscaTodos(false);
    }

    public List<ProdutoPronto> buscaTodos(boolean ehAdmin) {
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
        }
        return listaProdutos;
    }

    @Override
    public ProdutoPronto busca(int id) {
        String sql = "SELECT ProdutoPronto.nome, ProdutoPronto.valor, ProdutoPronto.estoque"
                + " FROM Produto INNER JOIN ProdutoPronto"
                + " ON Produto.id = ProdutoPronto.id"
                + " WHERE Produto.id=" + id + " AND tipo = 0;";
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
                        rs.getInt("estoque"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return produto;
    }

    @Override
    public int insere(ProdutoPronto produtoPronto) {
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
                pstmtProdutoPronto.setString(2, produtoPronto.getNome());
                pstmtProdutoPronto.setInt(3, produtoPronto.getEstoque());
                pstmtProdutoPronto.setDouble(4, produtoPronto.getValor());
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
        }
        return idProduto;
    }

    @Override
    public int atualiza(ProdutoPronto produtoPronto) {
        String sqlProdutoPronto = "UPDATE ProdutoPronto SET nome = ?, estoque = ?, valor = ? WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtProdutoPronto = conn.prepareStatement(sqlProdutoPronto);
            pstmtProdutoPronto.setString(1, produtoPronto.getNome());
            pstmtProdutoPronto.setInt(2, produtoPronto.getEstoque());
            pstmtProdutoPronto.setDouble(3, produtoPronto.getValor());
            pstmtProdutoPronto.setInt(4, produtoPronto.getId());
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

    public int atualizaEstoque(int id, int estoque) {
        if (estoque < 0) {
            return Util.RETORNO_ERRO_INDETERMINADO;
        }
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
        }
        return valorRetorno;
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
