/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import java.text.ParseException;

/**
 *
 * @author lugar
 */
public class PessoaJuridica extends Cliente {

    private String razaoSocial;

    public PessoaJuridica(int idUsuario, String nomeUsuario, String senhaHash) {
        super(idUsuario, nomeUsuario, senhaHash, false);
    }

    public PessoaJuridica(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            String identificador,
            String email,
            String telefone,
            Endereco endereco,
            String cartao,
            String razaoSocial
    ) {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email,
                telefone, endereco, cartao, false);
        this.razaoSocial = razaoSocial;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    @Override
    public String getIdentificadorFormatado() {
        try {
            return Util.formataString(this.getIdentificador(), "##.###.###/####-##");
        } catch (ParseException ex) {
            return this.getIdentificador();
        }
    }

}
