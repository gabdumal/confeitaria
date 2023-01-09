/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import com.lugar.model.exceptions.ExcecaoStringInvalido;
import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;
import java.text.ParseException;

/**
 *
 * @author lugar
 */
public class PessoaJuridica extends Cliente {

    private String razaoSocial;

    public PessoaJuridica(int idUsuario, String nomeUsuario, String senhaHash) throws ExcecaoUsuarioInvalido {
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
    ) throws ExcecaoUsuarioInvalido {
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

    @Override
    public boolean validaIdentificador(String identificador) throws ExcecaoStringInvalido {
        if (identificador.isBlank()) {
            throw new ExcecaoStringInvalido("identificador", false);
        }
        if (!identificador.matches("[0-9]+")) {
            throw new ExcecaoStringInvalido("identificador", "CNPJ deve conter apenas números.");
        }
        if (identificador.length() != 14) {
            throw new ExcecaoStringInvalido("identificador", "CNPJ deve ter 14 caracteres.");
        }
        return true;
    }
}
