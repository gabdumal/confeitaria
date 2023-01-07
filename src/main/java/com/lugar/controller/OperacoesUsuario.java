/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.model.Endereco;
import com.lugar.model.Funcionario;
import com.lugar.model.PessoaFisica;
import com.lugar.model.PessoaJuridica;
import com.lugar.model.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
    public Usuario busca(int id) {
        String sql = "SELECT Usuario.id, Usuario.nome, Usuario.nomeUsuario, Usuario.senhaHash, Usuario.identificador, Usuario.email, Usuario.telefone, Usuario.admin, Usuario.idEndereco, \n"
                + "Funcionario.matricula, Funcionario.funcao, Cliente.cartao, Cliente.fisica, PessoaFisica.dataNascimento, PessoaJuridica.razaoSocial,\n"
                + "Endereco.numero, Endereco.complemento, Endereco.logradouro, Endereco.bairro, Endereco.cidade, Endereco.uf, Endereco.cep\n"
                + "FROM Usuario LEFT JOIN Funcionario ON Usuario.id = Funcionario.id LEFT JOIN Cliente ON Usuario.id = Cliente.id LEFT JOIN PessoaFisica ON Cliente.id = PessoaFisica.id\n"
                + "LEFT JOIN PessoaJuridica ON Cliente.id = PessoaJuridica.id INNER JOIN Endereco ON Usuario.idEndereco = Endereco.id WHERE Usuario.id = " + id + ";";
        Connection conn = null;
        Usuario usuario = null;
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String nome = rs.getString("nome");
                String nomeUsuario = rs.getString("nomeUsuario");
                String senhaHash = rs.getString("senhaHash");
                boolean admin = rs.getInt("admin") == 1;
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                String identificador = rs.getString("identificador");
                int idEndereco = rs.getInt("idEndereco");
                Endereco endereco = new Endereco(rs.getString("numero"),
                        rs.getString("complemento"), rs.getString("logradouro"),
                        rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("uf"), rs.getString("cep"));

                if (rs.getInt("admin") == 1) {
                    String matricula = rs.getString("matricula");
                    String funcao = rs.getString("funcao");
                    usuario = new Funcionario(id, nome, nomeUsuario, senhaHash, admin,
                            email, telefone, identificador, endereco, matricula, funcao);
                } else {
                    String cartao = rs.getString("cartao");
                    if (rs.getInt("fisica") == 0) {
                        String razaoSocial = rs.getString("razaoSocial");
                        usuario = new PessoaJuridica(id, nome, nomeUsuario, senhaHash,
                                identificador, email, telefone, endereco, cartao, razaoSocial);
                    } else {
                        LocalDate dataNascimento = LocalDate.parse(rs.getString("dataNascimento"));
                        usuario = new PessoaFisica(idEndereco, nome, nomeUsuario, senhaHash,
                                identificador, email, telefone, endereco, cartao, dataNascimento);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return usuario;
    }

    @Override
    public int insere(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
