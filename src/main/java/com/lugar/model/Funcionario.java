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
public class Funcionario extends Usuario {

    private String matricula;
    private String funcao;

    public Funcionario(int idUsuario, String nomeUsuario, String senhaHash) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nomeUsuario, senhaHash, true);
    }

    public Funcionario(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            boolean admin,
            String email,
            String telefone,
            String identificador,
            Endereco endereco,
            String matricula,
            String funcao
    ) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email, telefone, admin, endereco);
        this.matricula = matricula;
        this.funcao = funcao;
    }

    @Override
    public String getIdentificadorFormatado() {
        try {
            return Util.formataString(this.getIdentificador(), "###.###.###-##");
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
            throw new ExcecaoStringInvalido("identificador", "CPF deve conter apenas números.");
        }
        if (identificador.length() != 11) {
            throw new ExcecaoStringInvalido("identificador", "CPF deve ter 11 caracteres.");
        }
        return true;
    }

}
