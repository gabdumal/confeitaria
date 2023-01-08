/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import static com.lugar.controller.Conexao.determinaValorErro;
import com.lugar.model.Bolo;
import com.lugar.model.Caracteristica;
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
public class OperacoesBolo implements OperacoesConexao<Bolo> {

    @Override
    public List<Bolo> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Bolo busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(Bolo bolo) {
        String sqlBuscaBolo = "SELECT ProdutoPersonalizado.id, Caracteristica.id as idRecheio\n"
                + "FROM ProdutoPersonalizado\n"
                + "INNER JOIN Bolo ON ProdutoPersonalizado.id = Bolo.id\n"
                + "INNER JOIN Bolo_Recheio ON Bolo_Recheio.idBolo = Bolo.id \n"
                + "INNER JOIN Caracteristica\n"
                + "ON Caracteristica.id = Bolo_Recheio.idRecheio\n"
                + "AND Bolo.idCobertura = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"T\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "AND ProdutoPersonalizado.idCor = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"C\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "AND Bolo.idForma = (SELECT DISTINCT id FROM Caracteristica WHERE Caracteristica.tipo=\"F\" AND Caracteristica.nome = ? LIMIT 1)\n"
                + "WHERE detalhe = ? AND Caracteristica.tipo=\"R\" ORDER BY ProdutoPersonalizado.id;";

        Connection conn = null;
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        try {
            // Se já existe, retorna ID. Senão, cria novo e retorna ID
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtBusca = conn.prepareStatement(sqlBuscaBolo);
            pstmtBusca.setString(1, bolo.getCobertura().getNome());
            pstmtBusca.setString(2, bolo.getCor().getNome());
            pstmtBusca.setString(3, bolo.getForma().getNome());
            pstmtBusca.setString(4, bolo.getDetalhe());
            ResultSet rsBusca = pstmtBusca.executeQuery();

            int indAux = 0;
            List<Caracteristica> recheios = bolo.getRecheios();
            int qtdRecheios = recheios.size();
            boolean naoBate = false;
            if (rsBusca.next()) {
                idProduto = rsBusca.getInt("id");
                qtdRecheios = recheios.size();
                do {
                    if ((indAux == qtdRecheios && !naoBate)) {
                        break;
                    }
                    int idAtual = rsBusca.getInt("id");
                    if (idAtual != idProduto) {
                        indAux = 0;
                        naoBate = false;
                    }
                    if (indAux == qtdRecheios
                            || recheios.get(indAux).getId() != rsBusca.getInt("idRecheio")) {
                        naoBate = true;
                    }
                    idProduto = idAtual;
                    indAux++;
                } while (rsBusca.next());
            } else {
                naoBate = true;
            }
            if (!naoBate) {
                // Existe
                return idProduto;
            } else {
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

                        String sqlProdutoPersonalizado = "INSERT INTO ProdutoPersonalizado(id, detalhe, receita, idCor) VALUES(?, ?, ?, ?);";
                        PreparedStatement pstmtProdutoPersonalizado = conn.prepareStatement(sqlProdutoPersonalizado,
                                Statement.RETURN_GENERATED_KEYS);
                        pstmtProdutoPersonalizado.setInt(1, idProduto);
                        pstmtProdutoPersonalizado.setString(2, bolo.getDetalhe());
                        pstmtProdutoPersonalizado.setString(3, bolo.getReceita());
                        pstmtProdutoPersonalizado.setInt(4, bolo.getCor().getId());
                        linhaInserida = pstmtProdutoPersonalizado.executeUpdate();

                        // Reverter operação em caso de erro
                        if (linhaInserida != 1) {
                            conn.rollback();
                        } else {
                            String sqlBolo = "INSERT INTO Bolo(id, idForma, idCobertura) VALUES(?, ?, ?);";
                            PreparedStatement pstmtBolo = conn.prepareStatement(sqlBolo,
                                    Statement.RETURN_GENERATED_KEYS);
                            pstmtBolo.setInt(1, idProduto);
                            pstmtBolo.setInt(2, bolo.getForma().getId());
                            pstmtBolo.setInt(3, bolo.getCobertura().getId());
                            linhaInserida = pstmtBolo.executeUpdate();

                            if (linhaInserida != 1) {
                                conn.rollback();
                            } else {
                                String sqlRecheio = "INSERT INTO Bolo_Recheio(idBolo, idRecheio) VALUES";

                                for (int i = 0; i < qtdRecheios; i++) {
                                    sqlRecheio = sqlRecheio.concat("(" + idProduto + ",  ?), ");
                                }
                                sqlRecheio = sqlRecheio.substring(0, sqlRecheio.length() - 2).concat(";");

                                PreparedStatement pstmtRecheio = conn.prepareStatement(sqlRecheio);
                                int i = 1;
                                for (Caracteristica recheio : recheios) {
                                    pstmtRecheio.setInt(i, recheio.getId());
                                    i++;
                                }
                                pstmtRecheio.executeUpdate();

                                conn.commit();
                            }
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
    public int atualiza(Bolo bolo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
