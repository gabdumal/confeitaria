/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import java.time.LocalDateTime;

/**
 *
 * @author lugar
 */
public class PessoaFisica extends Cliente {

    private LocalDateTime dataNascimento;

    public PessoaFisica(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            String identificador,
            String email,
            String telefone,
            Endereco endereco,
            String cartao,
            LocalDateTime dataNascimento
    ) {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email,
                telefone, endereco, cartao, true);
        this.dataNascimento = dataNascimento;
    }

}