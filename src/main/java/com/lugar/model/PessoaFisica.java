/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;
import java.text.ParseException;
import java.time.LocalDate;

/**
 *
 * @author lugar
 */
public class PessoaFisica extends Cliente {

    private LocalDate dataNascimento;

    public PessoaFisica(int idUsuario, String nomeUsuario, String senhaHash) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nomeUsuario, senhaHash, true);
    }

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
            LocalDate dataNascimento
    ) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email,
                telefone, endereco, cartao, true);
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    @Override
    public String getIdentificadorFormatado() {
        try {
            return Util.formataString(this.getIdentificador(), "###.###.###-##");
        } catch (ParseException ex) {
            return this.getIdentificador();
        }
    }

}
