/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Cliente;
import com.lugar.model.Endereco;
import com.lugar.model.PessoaFisica;
import com.lugar.model.PessoaJuridica;
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
        String sqlEndereco = "INSERT INTO Endereco(numero, complemento, logradouro, bairro, cidade, uf, cep) VALUES(?,?,?,?,?,?,?)";
        String sqlUsuario = "INSERT INTO Usuario(nome, nomeUsuario, senhaHash, identificador, email, telefone, admin, idEndereco) VALUES(?,?,?,?,?,?,0,?);";
        String sqlCliente = "INSERT INTO Cliente(id, cartao) VALUES(?,?);";
        String sqlPFPJ;

        Connection conn = null;
        int idUsuario = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            Endereco enderecoCliente = cliente.getEndereco();
            PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            pstmtEndereco.setString(1, enderecoCliente.getNumero());
            pstmtEndereco.setString(2, enderecoCliente.getComplemento());
            pstmtEndereco.setString(3, enderecoCliente.getLogradouro());
            pstmtEndereco.setString(4, enderecoCliente.getBairro());
            pstmtEndereco.setString(5, enderecoCliente.getCidade());
            pstmtEndereco.setString(6, enderecoCliente.getUf());
            pstmtEndereco.setString(7, enderecoCliente.getCep());
            int linhaInserida = pstmtEndereco.executeUpdate();

            // Reverter operação em caso de erro
            if (linhaInserida != 1) {
                conn.rollback();
            } else {
                ResultSet rs = pstmtEndereco.getGeneratedKeys();
                int idEndereco = 0;
                if (rs.next()) {
                    idEndereco = rs.getInt(1);
                }

                PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
                pstmtUsuario.setString(1, cliente.getNome());
                pstmtUsuario.setString(2, cliente.getNomeUsuario());
                pstmtUsuario.setString(3, cliente.getSenhaHash());
                pstmtUsuario.setString(4, cliente.getIdentificador());
                pstmtUsuario.setString(5, cliente.getEmail());
                pstmtUsuario.setString(6, cliente.getTelefone());
                pstmtUsuario.setInt(7, idEndereco);
                linhaInserida = pstmtUsuario.executeUpdate();

                if (linhaInserida != 1) {
                    conn.rollback();
                } else {
                    rs = pstmtUsuario.getGeneratedKeys();
                    if (rs.next()) {
                        idUsuario = rs.getInt(1);
                    }

                    PreparedStatement pstmtCliente = conn.prepareStatement(sqlCliente,
                            Statement.RETURN_GENERATED_KEYS);
                    pstmtCliente.setInt(1, idUsuario);
                    pstmtCliente.setString(2, cliente.getCartao());
                    linhaInserida = pstmtCliente.executeUpdate();

                    if (linhaInserida != 1) {
                        conn.rollback();
                    } else {
                        if (cliente instanceof PessoaFisica) {
                            sqlPFPJ = "INSERT INTO PessoaFisica(id, dataNascimento) VALUES(?,?);";
                            PreparedStatement pstmtPessoaFisica = conn.prepareStatement(sqlPFPJ,
                                    Statement.RETURN_GENERATED_KEYS);
                            pstmtPessoaFisica.setInt(1, idUsuario);
                            pstmtPessoaFisica.setString(2, ((PessoaFisica) cliente).getDataNascimento().toString());
                            linhaInserida = pstmtPessoaFisica.executeUpdate();
                        } else {
                            sqlPFPJ = "INSERT INTO PessoaJuridica(id, razaoSocial) VALUES(?,?);";
                            PreparedStatement pstmtPessoaJuridica = conn.prepareStatement(sqlPFPJ,
                                    Statement.RETURN_GENERATED_KEYS);
                            pstmtPessoaJuridica.setInt(1, idUsuario);
                            pstmtPessoaJuridica.setString(2, ((PessoaJuridica) cliente).getRazaoSocial());
                            linhaInserida = pstmtPessoaJuridica.executeUpdate();
                        }
                        if (linhaInserida != 1) {
                            conn.commit();
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
            idUsuario = Conexao.determinaValorErro(ex.getMessage());
        } finally {
            Conexao.fechaConexao(conn);
        }
        return idUsuario;
    }

    @Override
    public int atualiza(Cliente cliente) {
        String sqlUsuario = "UPDATE Usuario SET nome = ?, nomeUsuario = ?, senhaHash = ?, identificador = ?, email = ?, telefone = ?, idEndereco = ? WHERE id = ?;";
        String sqlCliente = "UPTADE Cliente SET cartao = ? WHERE id = ?;";
        String sqlEndereco = "UPDATE Endereco SET numero = ?, complemento = ?, logradouro = ?, bairro = ?, cidade = ?, uf = ? , cep = ? WHERE id = ?;";
        String sqlPFPJ;

        Connection conn = null;
        int valorRetorno = Util.RETORNO_SUCESSO;
        try {
            conn = Conexao.abreConexao();
            conn.setAutoCommit(false);

            Endereco enderecoCliente = cliente.getEndereco();
            PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            pstmtEndereco.setString(1, enderecoCliente.getNumero());
            pstmtEndereco.setString(2, enderecoCliente.getComplemento());
            pstmtEndereco.setString(3, enderecoCliente.getLogradouro());
            pstmtEndereco.setString(4, enderecoCliente.getBairro());
            pstmtEndereco.setString(5, enderecoCliente.getCidade());
            pstmtEndereco.setString(6, enderecoCliente.getUf());
            pstmtEndereco.setString(7, enderecoCliente.getCep());
            int linhaInserida = pstmtEndereco.executeUpdate();

            // Reverter operação em caso de erro
            if (linhaInserida != 1) {
                conn.rollback();
            } else {
                ResultSet rs = pstmtEndereco.getGeneratedKeys();
                if (rs.next()) {
                    int idEndereco = rs.getInt(1);

                    PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsuario);
                    pstmtUsuario.setString(1, cliente.getNome());
                    pstmtUsuario.setString(2, cliente.getNomeUsuario());
                    pstmtUsuario.setString(3, cliente.getSenhaHash());
                    pstmtUsuario.setString(4, cliente.getIdentificador());
                    pstmtUsuario.setString(5, cliente.getEmail());
                    pstmtUsuario.setString(6, cliente.getTelefone());
                    pstmtUsuario.setString(6, cliente.getTelefone());
                    pstmtUsuario.setInt(7, idEndereco);
                    pstmtUsuario.setInt(8, cliente.getId());

                    int linhaAtualizada = pstmtUsuario.executeUpdate();

                    // Reverter operação em caso de erro
                    if (linhaAtualizada != 1) {
                        conn.rollback();
                    } else {
                        PreparedStatement pstmtCliente = conn.prepareStatement(sqlCliente);
                        pstmtCliente.setString(1, cliente.getCartao());
                        pstmtCliente.setInt(2, cliente.getId());

                        linhaAtualizada = pstmtCliente.executeUpdate();

                        if (linhaAtualizada != -1) {

                            if (cliente instanceof PessoaFisica) {
                                sqlPFPJ = "UPDATE PessoaFisica SET dataNascimento = ? WHERE id = ?;";
                                PreparedStatement pstmtPessoaFisica = conn.prepareStatement(sqlPFPJ);
                                pstmtPessoaFisica.setString(1, ((PessoaFisica) cliente).getDataNascimento().toString());
                                pstmtPessoaFisica.setInt(2, cliente.getId());
                                linhaAtualizada = pstmtPessoaFisica.executeUpdate();
                            } else {
                                sqlPFPJ = "UPDATE PessoaJuridica SET razaoSocial = ? WHERE id = ?;";
                                PreparedStatement pstmtPessoaJuridica = conn.prepareStatement(sqlPFPJ);
                                pstmtPessoaJuridica.setString(1, ((PessoaJuridica) cliente).getRazaoSocial());
                                pstmtPessoaJuridica.setInt(2, cliente.getId());
                                linhaAtualizada = pstmtPessoaJuridica.executeUpdate();
                            }
                            if (linhaAtualizada != 1) {
                                conn.rollback();
                            } else {
                                conn.commit();
                            }
                        }
                    }
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
