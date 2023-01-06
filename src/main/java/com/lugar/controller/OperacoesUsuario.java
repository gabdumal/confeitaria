/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.model.Funcionario;
import com.lugar.model.PessoaFisica;
import com.lugar.model.PessoaJuridica;
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

    @Override
    public List<Usuario> buscaTodos() {

        Connection conn = null;
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        try {
            conn = Conexao.abreConexao();
            if (conn == null) {
                return listaUsuarios;
            }
            String sql = "SELECT Usuario.id, Usuario.nomeUsuario, Usuario.senhaHash, Usuario.admin, Cliente.fisica FROM Usuario LEFT JOIN Cliente ON Usuario.id = Cliente.id;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario;
                if (rs.getInt("admin") == 1) {
                    usuario = new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nomeUsuario"),
                            rs.getString("senhaHash"));
                } else if (rs.getInt("fisica") == 0) {
                    usuario = new PessoaJuridica(
                            rs.getInt("id"),
                            rs.getString("nomeUsuario"),
                            rs.getString("senhaHash"));
                } else {
                    usuario = new PessoaFisica(
                            rs.getInt("id"),
                            rs.getString("nomeUsuario"),
                            rs.getString("senhaHash"));
                }
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
    public int deleta(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(Usuario objeto
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(Usuario objeto
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario busca(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
