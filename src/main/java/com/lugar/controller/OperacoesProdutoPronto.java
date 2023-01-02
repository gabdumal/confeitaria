/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.model.ProdutoPronto;
import java.sql.Connection;
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
public class OperacoesProdutoPronto implements OperacoesConexao<ProdutoPronto> {

    @Override
    public List<ProdutoPronto> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public int insere(ProdutoPronto objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(ProdutoPronto objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
