/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conexao;
    }

    /**
     * Seleciona todas as linhas em uma tabela
     */
    private String constroiQuery(String[] campos, String tabela) {
        String sql = "SELECT ";
        for (int i = 0; i < campos.length; i++) {
            sql += campos[i];
            if (i == campos.length - 1) {
                sql += " ";
            } else {
                sql += ", ";
            }
        }
        sql += "FROM " + tabela + ";";
        return sql;
    }

    public ArrayList<Usuario> buscaTodosUsuarios() {
        String sql = constroiQuery(new String[]{"id", "nome", "nomeUsuario", "senhaHash", "admin"}, "Usuario");
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
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

    public ArrayList<Produto> buscaTodosProdutos() {
        String sql = constroiQuery(new String[]{"id", "nome", "valor", "quantidade"}, "Produto");
        try ( Connection conexao = this.criaConexao();  Statement stmt = conexao.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
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
}
