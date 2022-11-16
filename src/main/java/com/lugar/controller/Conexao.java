/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.model.Usuario;
import com.lugar.model.Produto;
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
    private Connection criaConexao() {
        String url = "jdbc:sqlite:confeitaria.db";
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexao;
    }

    public List<Usuario> buscaTodosUsuariosLogin() {
        String sql = "SELECT id, nomeUsuario, senhaHash, admin FROM Usuario;";
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            List<Usuario> listaUsuarios = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nomeUsuario"),
                        rs.getString("senhaHash"),
                        rs.getInt("admin") == 1
                );
                listaUsuarios.add(usuario);
            }
            return listaUsuarios;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int insereUsuario(String nome, String nomeUsuario, String senhaHash) {
        String sql = "INSERT INTO Usuario(nome, nomeUsuario, senhaHash, admin) VALUES(?, ?, ?, ?);";
        try ( Connection conexao = this.criaConexao();  PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, nomeUsuario);
            pstmt.setString(3, senhaHash);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            if (ex.getMessage().substring(0, 26)
                    .compareTo("[SQLITE_CONSTRAINT_UNIQUE]") == 0) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public int insereCliente(Usuario usuario) {
        String sql = "INSERT INTO Usuario(nome, nomeUsuario, senhaHash, admin, email, telefone, endereco, cartao, identificador) VALUES(?, ?, ?, 0, ?, ?, ?, ?, ?);";
        try ( Connection conexao = this.criaConexao();  PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getNomeUsuario());
            pstmt.setString(3, usuario.getSenhaHash());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getTelefone());
            pstmt.setString(6, usuario.getEndereco());
            pstmt.setString(7, usuario.getCartao());
            pstmt.setString(8, usuario.getIdentificador());

            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            if (ex.getMessage().substring(0, 26)
                    .compareTo("[SQLITE_CONSTRAINT_UNIQUE]") == 0) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public Produto buscaProduto(int id) {
        String sql = "SELECT nome, valor, quantidade FROM Produto WHERE id=" + id + ";";
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            Produto produto;
            if (rs.next()) {
                produto = new Produto(
                        id,
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getInt("quantidade")
                );
            } else {
                produto = new Produto(id, "", 0, 0);
            }
            return produto;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int buscaEstoqueProduto(int id) {
        String sql = "SELECT quantidade FROM Produto WHERE id=" + id + ";";
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("quantidade");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public List<Produto> buscaTodosProdutos() {
        String sql = "SELECT id, nome, valor, quantidade FROM Produto;";
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            List<Produto> listaProdutos = new ArrayList<Produto>();
            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getInt("quantidade")
                );
                listaProdutos.add(produto);
            }
            return listaProdutos;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int insereProduto(String nome, double valor) {
        String sql = "INSERT INTO Produto(nome, valor, quantidade) VALUES(?, ?, 0);";
        try ( Connection conexao = this.criaConexao();  PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setDouble(2, valor);
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            if (ex.getMessage().substring(0, 26)
                    .compareTo("[SQLITE_CONSTRAINT_UNIQUE]") == 0) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public int atualizaProduto(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, valor = ? WHERE id = ?;";
        try ( Connection conexao = this.criaConexao();  PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getValor());
            pstmt.setInt(3, produto.getId());
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    public int atualizaEstoqueProduto(int id, int estoque) {
        String sql = "UPDATE Produto SET quantidade = ? WHERE id = ?;";
        try ( Connection conexao = this.criaConexao();  PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, estoque);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    public int deletaProduto(int idProduto) {
        String sql = "DELETE FROM Produto WHERE id = ?;";
        try ( Connection conexao = this.criaConexao();  PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idProduto);
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName())
                    .log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    public List<Transacao> buscaTodasAsTransacoes() {
        String sql = "SELECT id, valor, diaHora, descricao FROM Transacao;";
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            List<Transacao> listaTransacoes = new ArrayList<Transacao>();
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
            return listaTransacoes;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
