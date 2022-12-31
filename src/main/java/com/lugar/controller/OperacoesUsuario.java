/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.model.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lugar
 */
public class OperacoesUsuario implements OperacoesConexao<Usuario> {

    public OperacoesUsuario() {
    }

    @Override
    public List<Usuario> buscaTodos() {

        Connection conn = null;
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        try {
            conn = Conexao.abreConexao();
            if (conn == null) {
                return listaUsuarios;
            }
            String sql = "SELECT id, nomeUsuario, senhaHash, admin FROM Usuario;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nomeUsuario"),
                        rs.getString("senhaHash"),
                        rs.getInt("admin") == 1);
                listaUsuarios.add(usuario);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return listaUsuarios;
    }


    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(Usuario objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(Usuario objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
