/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Cliente;
import com.lugar.model.Endereco;
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
public class OperacoesCliente implements OperacoesConexao<Cliente> {

    @Override
    public List<Cliente> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(Cliente cliente) {
        String sqlUsuario = "INSERT INTO Usuario(nome, nomeUsuario, senhaHash, admin, email, telefone) VALUES(?, ?, ?, 0, ?, ?);";
        String sqlCliente = "INSERT INTO Cliente(id, idEndereco, cartao, identificador) VALUES(?,?,?,?);";
        String sqlEndereco = "INSERT INTO Endereco(numero, complemento, logradouro, bairro, cidade, uf, cep) VALUES(?,?,?,?,?,?,?)";

        Connection conn = null;
        int idUsuario = Util.RETORNO_SUCESSO;
        int idEndereco = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);

            pstmtUsuario.setString(1, cliente.getNome());
            pstmtUsuario.setString(2, cliente.getNomeUsuario());
            pstmtUsuario.setString(3, cliente.getSenhaHash());
            pstmtUsuario.setString(4, cliente.getEmail());
            pstmtUsuario.setString(5, cliente.getTelefone());

            int linhaInserida = pstmtUsuario.executeUpdate();
            // Reverter operação em caso de erro
            if (linhaInserida != 1) {
                conn.rollback();
            } else {
                Endereco enderecoCliente = cliente.getEndereco();
                PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);

                pstmtEndereco.setString(1, enderecoCliente.getNumero());
                pstmtEndereco.setString(2, enderecoCliente.getComplemento());
                pstmtEndereco.setString(3, enderecoCliente.getLogradouro());
                pstmtEndereco.setString(4, enderecoCliente.getBairro());
                pstmtEndereco.setString(5, enderecoCliente.getCidade());
                pstmtEndereco.setString(6, enderecoCliente.getUf());
                pstmtEndereco.setString(7, enderecoCliente.getCep());
                int linhaInserida2 = pstmtEndereco.executeUpdate();
                if (linhaInserida2 != 1) {
                    conn.rollback();
                } else {
                    ResultSet rs = pstmtUsuario.getGeneratedKeys();
                    if (rs.next()) {
                        idUsuario = rs.getInt(1);
                    }
                    rs = pstmtEndereco.getGeneratedKeys();

                    if (rs.next()) {
                        idEndereco = rs.getInt(1);
                    }

                    PreparedStatement pstmtCliente = conn.prepareStatement(sqlCliente,
                            Statement.RETURN_GENERATED_KEYS);
                    pstmtCliente.setInt(1, idUsuario);
                    pstmtCliente.setInt(2, idEndereco);
                    pstmtCliente.setString(3, cliente.getCartao());
                    pstmtCliente.setString(4, cliente.getIdentificador());

                    pstmtCliente.executeUpdate();
                    conn.commit();
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
            idUsuario = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
        }
        return idUsuario;
    }

    @Override
    public int atualiza(Cliente objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
