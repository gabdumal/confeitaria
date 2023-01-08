/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Bolo;
import com.lugar.model.Caracteristica;
import com.lugar.model.Forma;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.Trufa;
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
        String sql = "SELECT DISTINCT Produto.id, Produto.tipo as tipoProduto, ProdutoPronto.nome, ProdutoPronto.estoque, ProdutoPronto.valor, \n"
                + "ProdutoPersonalizado.detalhe, ProdutoPersonalizado.receita, Caracteristica.id AS idCaracteristica, \n"
                + "Caracteristica.nome AS caracteristica, Caracteristica.tipo, Caracteristica.valorGrama, Forma.recheios, Forma.gramaRecheio, \n"
                + "Forma.gramaCobertura, Forma.gramaMassa\n"
                + "FROM Produto\n"
                + "LEFT JOIN ProdutoPronto ON Produto.id = ProdutoPronto.id\n"
                + "LEFT JOIN ProdutoPersonalizado ON Produto.id = ProdutoPersonalizado.id\n"
                + "LEFT JOIN Bolo ON ProdutoPersonalizado.id = Bolo.id\n"
                + "LEFT JOIN Trufa ON ProdutoPersonalizado.id = Trufa.id\n"
                + "LEFT JOIN Bolo_Recheio ON Bolo.id = Bolo_Recheio.idBolo\n"
                + "LEFT JOIN Caracteristica ON Bolo.idForma = Caracteristica.id\n"
                + "OR Bolo.idCobertura = Caracteristica.id\n"
                + "OR Trufa.idRecheio = Caracteristica.id\n"
                + "OR ProdutoPersonalizado.idCor = Caracteristica.id\n"
                + "OR Bolo_Recheio.idRecheio = Caracteristica.id\n"
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
                    String receita = rs.getString("receita");
                    if (receita.equals(Util.RECEITA_BOLO)) {
                        produto = new Bolo(id, rs.getString("detalhe"));
                    } else {
                        produto = new Trufa(id, rs.getString("detalhe"));
                    }
                    // Preenche características
                    do {
                        int idCaracteristica = rs.getInt("idCaracteristica");
                        String tipo = rs.getString("tipo");
                        if (tipo.equals(Util.CARACTERISTICA_FORMA)) {
                            Forma forma = new Forma(
                                    idCaracteristica,
                                    tipo,
                                    rs.getString("caracteristica"),
                                    rs.getDouble("valorGrama"),
                                    rs.getInt("recheio"),
                                    rs.getDouble("gramaRecheio"),
                                    rs.getDouble("gramaCobertura"),
                                    rs.getDouble("gramaMassa")
                            );
                            ((Bolo) produto).setForma((Forma) forma);
                        } else {
                            Caracteristica caracteristica = new Caracteristica(
                                    idCaracteristica,
                                    tipo,
                                    rs.getString("caracteristica"),
                                    rs.getDouble("valorGrama"));
                            switch (tipo) {
                                case Util.CARACTERISTICA_RECHEIO:
                                    if (produto instanceof Bolo) {
                                        ((Bolo) produto).addRecheio(caracteristica);
                                    } else {
                                        ((Trufa) produto).setRecheio(caracteristica);
                                    }
                                    break;
                                case Util.CARACTERISTICA_COR:
                                    ((ProdutoPersonalizado) produto).setCor(caracteristica);
                                    break;
                                case Util.CARACTERISTICA_COBERTURA:
                                    ((Bolo) produto).setCobertura(caracteristica);
                                    break;
                            }
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
