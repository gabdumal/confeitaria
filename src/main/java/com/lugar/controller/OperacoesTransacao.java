/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import static com.lugar.controller.Conexao.determinaValorErro;
import com.lugar.model.Pedido;
import com.lugar.model.Transacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
public class OperacoesTransacao implements OperacoesConexao<Transacao> {

    @Override
    public List<Transacao> buscaTodos() {
        String sql = "SELECT id, valor, diaHora, descricao, ehPedido FROM Transacao;";
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
                        rs.getString("descricao"),
                        (rs.getInt("ehPedido") == 1)
                );
                listaTransacoes.add(transacao);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return listaTransacoes;
    }

    @Override
    public Transacao busca(int id) {
        String sql = "SELECT Transacao.valor, Transacao.descricao, Transacao.diaHora, Transacao.ehPedido FROM Transacao WHERE Transacao.id = " + id + ";";
        Connection conn = null;
        Transacao transacao = null;
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("diaHora"));
                if (rs.getInt("ehPedido") == 1) {
                    transacao = new Pedido(id, rs.getDouble("valor"),
                            dataHora, rs.getString("descricao"));
                } else {
                    transacao = new Transacao(id, rs.getDouble("valor"),
                            dataHora, rs.getString("descricao"),
                            (rs.getInt("ehPedido") == 1)
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return transacao;
    }

    @Override
    public int insere(Transacao transacao) {
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
        }
        return valorRetorno;
    }

    @Override
    public int atualiza(Transacao transacao) {
        String sqlTransacao = "UPDATE Transacao SET valor = ?, descricao = ? WHERE id = ?;";
        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            PreparedStatement pstmt = conn.prepareStatement(sqlTransacao);
            pstmt.setDouble(1, transacao.getValor());
            pstmt.setString(2, transacao.getDescricao());
            pstmt.setInt(3, transacao.getId());
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
        String sql = "DELETE FROM Transacao WHERE id = ?;";
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
