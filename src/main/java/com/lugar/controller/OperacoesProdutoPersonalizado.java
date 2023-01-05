/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import static com.lugar.controller.Conexao.determinaValorErro;
import com.lugar.model.Caracteristica;
import com.lugar.model.ProdutoPersonalizado;
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
public class OperacoesProdutoPersonalizado implements OperacoesConexao<ProdutoPersonalizado> {

    @Override
    public List<ProdutoPersonalizado> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProdutoPersonalizado busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(ProdutoPersonalizado produtoPersonalizado) {
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
        }
        return idProduto;
    }

    @Override
    public int atualiza(ProdutoPersonalizado objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
