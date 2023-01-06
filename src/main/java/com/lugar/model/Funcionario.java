/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class Funcionario extends Usuario {

    private String matricula;
    private String funcao;

    public Funcionario(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            boolean admin,
            String email,
            String telefone,
            int IdEndereco,
            String cartao,
            String identificador,
            Endereco endereco,
            String matricula,
            String funcao
    ) {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email, telefone, admin, endereco);
        this.matricula = matricula;
        this.funcao = funcao;
    }

}
