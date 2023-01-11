/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import static com.lugar.controller.Conexao.determinaValorErro;
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
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public class OperacoesTrufa implements OperacoesConexao<Trufa> {

    @Override
    public List<Trufa> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Trufa busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(Trufa trufa) {
        String sqlBuscaTrufa = "SELECT DISTINCT ProdutoPersonalizado.id\n"
                + "FROM ProdutoPersonalizado\n"
                + "INNER JOIN Trufa ON ProdutoPersonalizado.id = Trufa.id\n"
                + "INNER JOIN Caracteristica\n"
                + "ON ProdutoPersonalizado.idCor = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"C\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "AND Trufa.idRecheio = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"R\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "WHERE detalhe = ? ORDER BY ProdutoPersonalizado.id;";

        Connection conn = null;
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        try {
            // Se já existe, retorna ID. Senão, cria novo e retorna ID
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtBusca = conn.prepareStatement(sqlBuscaTrufa);
            pstmtBusca.setString(1, trufa.getCor().getNome());
            pstmtBusca.setString(2, trufa.getRecheio().getNome());
            pstmtBusca.setString(3, trufa.getDetalhe());
            ResultSet rsBusca = pstmtBusca.executeQuery();

            if (rsBusca.next()) {
                idProduto = rsBusca.getInt("id");
            }else{
                String sqlProduto = "INSERT INTO Produto(tipo) VALUES(1);";
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
                    String sqlProdutoPersonalizado = "INSERT INTO ProdutoPersonalizado(id, detalhe, receita, idCor) VALUES(?, ?, ?, ?);";
                    PreparedStatement pstmtProdutoPersonalizado = conn.prepareStatement(sqlProdutoPersonalizado,
                            Statement.RETURN_GENERATED_KEYS);
                    pstmtProdutoPersonalizado.setInt(1, idProduto);
                    pstmtProdutoPersonalizado.setString(2, trufa.getDetalhe());
                    pstmtProdutoPersonalizado.setString(3, trufa.getReceita());
                    pstmtProdutoPersonalizado.setInt(4, trufa.getCor().getId());
                    linhaInserida = pstmtProdutoPersonalizado.executeUpdate();

                    // Reverter operação em caso de erro
                    if (linhaInserida != 1) {
                        conn.rollback();
                    } else {
                        String sqlTrufa = "INSERT INTO Trufa(id, idRecheio) VALUES(?, ?);";
                        PreparedStatement pstmtTrufa = conn.prepareStatement(sqlTrufa,
                                Statement.RETURN_GENERATED_KEYS);
                        pstmtTrufa.setInt(1, idProduto);
                        pstmtTrufa.setInt(2, trufa.getRecheio().getId());
                        linhaInserida = pstmtTrufa.executeUpdate();

                        if (linhaInserida != 1) {
                            conn.rollback();
                        } else {
                            conn.commit();
                        }
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
        }
        return idProduto;
    }

    @Override
    public int atualiza(Trufa trufa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
