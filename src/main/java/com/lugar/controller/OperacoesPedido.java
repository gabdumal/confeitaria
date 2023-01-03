/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Item;
import com.lugar.model.Pedido;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.exceptions.ExcecaoNovoEstoqueInvalido;
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
 */
public class OperacoesPedido implements OperacoesConexao<Pedido> {

    @Override
    public List<Pedido> buscaTodos() {
        String sql = "SELECT Transacao.id, Transacao.valor, Transacao.diaHora, Transacao.ehPedido, Pedido.estado, Pedido.dataEntrega FROM Transacao INNER JOIN Pedido ON Transacao.id = Pedido.id WHERE Transacao.ehPedido = 1;";
        Connection conn = null;
        List<Pedido> listaPedidos = new ArrayList<Pedido>();
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LocalDateTime diaHora = LocalDateTime.parse(rs.getString("diaHora"));
                LocalDateTime dataEntrega = LocalDateTime.parse(rs.getString("dataEntrega"));
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("valor"),
                        diaHora,
                        dataEntrega,
                        rs.getString("estado")
                );
                listaPedidos.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return listaPedidos;
    }

    @Override
    public Pedido busca(int id) {
        String sql = "SELECT Transacao.id, Transacao.diaHora, Transacao.ehPedido, Pedido.estado, Pedido.dataEntrega, Pedido.comentario, Pedido.idCliente, \n"
                + "Item.id AS idItem, Item.idProduto, Item.quantidade, Item.valorTotal \n"
                + "FROM Transacao INNER JOIN Pedido ON Transacao.id = Pedido.id\n"
                + "INNER JOIN Item ON transacao.id = Item.idPedido\n"
                + "WHERE Transacao.id = " + id + "\n"
                + "ORDER BY Transacao.id, Item.id;";

        Connection conn = null;
        Pedido pedido = null;

        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Cria Pedido
            LocalDateTime diaHora = null;
            String estado = null;
            LocalDateTime dataEntrega = null;
            String comentario = null;
            boolean primeiro = true;
            List<Item> listaItens = new ArrayList<Item>();
            int idCliente = 0;
            OperacoesProduto operacoesProduto = new OperacoesProduto();

            while (rs.next()) {
                if (primeiro) {
                    diaHora = LocalDateTime.parse(rs.getString("diaHora"));
                    estado = rs.getString("estado");
                    dataEntrega = LocalDateTime.parse(rs.getString("dataEntrega"));
                    comentario = rs.getString("comentario");
                    idCliente = rs.getInt("idCliente");
                    primeiro = false;
                }

                int idItem = rs.getInt("idItem");
                int idProduto = rs.getInt("idProduto");
                int quantidade = rs.getInt("quantidade");;
                double valorTotal = rs.getDouble("valorTotal");
                Produto produto = operacoesProduto.busca(idProduto);
                Item item = new Item(idItem, valorTotal, produto, quantidade);
                listaItens.add(item);
            }

            if (!primeiro) {
                pedido = new Pedido(id, diaHora, estado, dataEntrega, comentario, listaItens, idCliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return pedido;
    }

    @Override
    public int insere(Pedido pedido) {
        String sqlTransacao = "INSERT INTO Transacao(valor, diaHora, descricao, ehPedido) VALUES(?, ?, ?, 1);";
        String sqlPedido = "INSERT INTO Pedido(id, estado, dataEntrega, comentario, idCliente) VALUES(?, ?, ?, ?, ?);";

        Connection conn = null;
        int idTransacao = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtTransacao = conn.prepareStatement(sqlTransacao, Statement.RETURN_GENERATED_KEYS);
            pstmtTransacao.setDouble(1, pedido.getValor());
            pstmtTransacao.setString(2, pedido.getDiaHoraString());
            pstmtTransacao.setString(3, pedido.getDescricao());
            int linhasInseridas = pstmtTransacao.executeUpdate();

            // Reverter operação em caso de erro
            if (linhasInseridas != 1) {
                conn.rollback();
            } else {
                ResultSet rs = pstmtTransacao.getGeneratedKeys();
                if (rs.next()) {
                    idTransacao = rs.getInt(1);
                }

                PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                pstmtPedido.setInt(1, idTransacao);
                pstmtPedido.setString(2, pedido.getEstado());
                pstmtPedido.setString(3, pedido.getDataEntregaString());
                pstmtPedido.setString(4, pedido.getComentario());
                pstmtPedido.setInt(5, pedido.getIdCliente());
                linhasInseridas = pstmtPedido.executeUpdate();

                // Reverter operação em caso de erro
                if (linhasInseridas != 1) {
                    conn.rollback();
                } else {
                    // Constroi comando SQL
                    List<Item> listaItens = pedido.getListaItens();
                    String sqlItem = "INSERT INTO Item(valorTotal, quantidade, idProduto, idPedido) VALUES";

                    // Insere valores de cada item
                    for (Item item : listaItens) {
                        Produto produto = item.getProduto();

                        // Reduz estoque de produto pronto
                        if (produto instanceof ProdutoPronto) {
                            int novoEstoque = ((ProdutoPronto) produto).getEstoque() - item.getQuantidade();
                            if (novoEstoque < 0) {
                                conn.rollback();
                                throw new ExcecaoNovoEstoqueInvalido();
                            }
                            String sqlProdutoPronto = "UPDATE ProdutoPronto SET estoque = ? WHERE id = ?;";
                            PreparedStatement pstmtProdutoPronto = conn.prepareStatement(sqlProdutoPronto);
                            pstmtProdutoPronto.setInt(1, novoEstoque);
                            pstmtProdutoPronto.setInt(2, produto.getId());
                            pstmtProdutoPronto.executeUpdate();
                        }

                        // Insere item no comando de inserção
                        sqlItem = sqlItem.concat("(" + item.getValorTotal() + " , "
                                + item.getQuantidade() + ", " + produto.getId() + ", "
                                + idTransacao + "), ");
                    }
                    sqlItem = sqlItem.substring(0, sqlItem.length() - 2).concat(";");

                    // Executa statement
                    Statement stmtItem = conn.createStatement();
                    linhasInseridas = stmtItem.executeUpdate(sqlItem);

                    if (linhasInseridas > 0) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }
                }
            }
            conn.setAutoCommit(true);
        } catch (SQLException | ExcecaoNovoEstoqueInvalido ex) {
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
            idTransacao = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
        }
        return idTransacao;
    }

    @Override
    public int atualiza(Pedido objeto
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
