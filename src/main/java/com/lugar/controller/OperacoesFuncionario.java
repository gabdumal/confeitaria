/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Endereco;
import com.lugar.model.Funcionario;
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
public class OperacoesFuncionario implements OperacoesConexao<Funcionario> {

    @Override
    public List<Funcionario> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Funcionario busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(Funcionario funcionario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(Funcionario funcionario) {
        String sqlUsuario = "UPDATE Usuario SET nome = ?, nomeUsuario = ?, senhaHash = ?, identificador = ?, email = ?, telefone = ?, idEndereco = ? WHERE id = ?;";
        String sqlEndereco = "UPDATE Endereco SET numero = ?, complemento = ?, logradouro = ?, bairro = ?, cidade = ?, uf = ? , cep = ? WHERE id = ?;";

        Connection conn = null;
        int valorRetorno = Util.RETORNO_ERRO_INDETERMINADO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            Endereco enderecoFuncionario = funcionario.getEndereco();
            int idEndereco = enderecoFuncionario.getId();
            PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            pstmtEndereco.setString(1, enderecoFuncionario.getNumero());
            pstmtEndereco.setString(2, enderecoFuncionario.getComplemento());
            pstmtEndereco.setString(3, enderecoFuncionario.getLogradouro());
            pstmtEndereco.setString(4, enderecoFuncionario.getBairro());
            pstmtEndereco.setString(5, enderecoFuncionario.getCidade());
            pstmtEndereco.setString(6, enderecoFuncionario.getUf());
            pstmtEndereco.setString(7, enderecoFuncionario.getCep());
            pstmtEndereco.setInt(8, idEndereco);
            int linhasAtualizadas = pstmtEndereco.executeUpdate();

            // Reverter operação em caso de erro
            if (linhasAtualizadas != 1) {
                conn.rollback();
            } else {
                PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsuario);
                pstmtUsuario.setString(1, funcionario.getNome());
                pstmtUsuario.setString(2, funcionario.getNomeUsuario());
                pstmtUsuario.setString(3, funcionario.getSenhaHash());
                pstmtUsuario.setString(4, funcionario.getIdentificador());
                pstmtUsuario.setString(5, funcionario.getEmail());
                pstmtUsuario.setString(6, funcionario.getTelefone());
                pstmtUsuario.setInt(7, idEndereco);
                pstmtUsuario.setInt(8, funcionario.getId());

                int linhaAtualizada = pstmtUsuario.executeUpdate();

                // Reverter operação em caso de erro
                if (linhaAtualizada != 1) {
                    conn.rollback();
                } else {
                    valorRetorno = Util.RETORNO_SUCESSO;
                    conn.commit();
                }
            }
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName())
                    .log(Level.SEVERE, null, ex);
            valorRetorno = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
            return valorRetorno;
        }
    }

    @Override
    public int deleta(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
