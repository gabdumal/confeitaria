/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Caracteristica;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.ProdutoPronto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lugar
 */
public class OperacoesProduto implements OperacoesConexao<Produto> {

    @Override
    public List<Produto> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Produto busca(int id) {
        String sql = "SELECT DISTINCT Produto.id, Produto.tipo as tipoProduto, ProdutoPronto.valor, ProdutoPronto.estoque, ProdutoPronto.nome,\n"
                + "ProdutoPersonalizado.detalhe, ProdutoPersonalizado.receita, Caracteristica.id AS idCaracteristica, Caracteristica.nome AS caracteristica, Caracteristica.tipo, Caracteristica.valorGrama,\n"
                + "Forma.recheios, Forma.gramaRecheio, Forma.gramaCobertura, Forma.gramaMassa\n"
                + "FROM Produto\n"
                + "LEFT JOIN ProdutoPronto ON Produto.id = ProdutoPronto.id\n"
                + "LEFT JOIN ProdutoPersonalizado ON Produto.id = ProdutoPersonalizado.id\n"
                + "LEFT JOIN ProdutoPersonalizado_Recheio ON ProdutoPersonalizado.id = ProdutoPersonalizado_Recheio.idProdutoPersonalizado\n"
                + "LEFT JOIN Caracteristica ON ProdutoPersonalizado.idForma = Caracteristica.id\n"
                + "OR ProdutoPersonalizado.idCobertura = Caracteristica.id\n"
                + "OR ProdutoPersonalizado.idCor = Caracteristica.id\n"
                + "OR ProdutoPersonalizado_Recheio.idRecheio = Caracteristica.id\n"
                + "LEFT JOIN Forma ON Caracteristica.id = Forma.id\n"
                + "WHERE Produto.id = " + id + "\n"
                + "ORDER BY Produto.id;";
        Connection conn = null;
        Produto produto = null;

        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Cria Produto Pronto ou Personalizado
            if (rs.next()) {
                if (rs.getInt("tipoProduto") == Util.TIPO_PRONTO) {
                    produto = new ProdutoPronto(
                            id,
                            rs.getString("nome"),
                            rs.getDouble("valor"),
                            rs.getInt("estoque"));
                } else {
                    produto = new ProdutoPersonalizado(
                            id,
                            rs.getString("receita"),
                            rs.getString("detalhe"));
                    // Preenche características
                    do {
                        int idCaracteristica = rs.getInt("idCaracteristica");
                        String tipo = rs.getString("tipo");
                        Caracteristica caracteristica = new Caracteristica(
                                idCaracteristica,
                                tipo,
                                rs.getString("caracteristica"),
                                rs.getDouble("valorGrama"));
                        switch (tipo) {
                            case Util.CARACTERISTICA_RECHEIO:
                                ((ProdutoPersonalizado) produto).addRecheio(caracteristica);
                                break;
                            case Util.CARACTERISTICA_COR:
                                ((ProdutoPersonalizado) produto).setCor(caracteristica);
                                break;
                            case Util.CARACTERISTICA_COBERTURA:
                                ((ProdutoPersonalizado) produto).setCobertura(caracteristica);
                                break;
                            case Util.CARACTERISTICA_FORMA:
                                ((ProdutoPersonalizado) produto).setForma(caracteristica);
                                break;
                        }
                    } while (rs.next());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return produto;
    }

    @Override
    public int insere(Produto objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(Produto objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        String sql = "DELETE FROM Produto WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
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
