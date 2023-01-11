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
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
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
            String email,
            String telefone,
            String identificador,
            Endereco endereco
    ) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email, telefone, true, endereco);
    }

    public Funcionario(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            String email,
            String telefone,
            String identificador,
            Endereco endereco,
            String matricula,
            String funcao
    ) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email, telefone, true, endereco);
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

    public String getMatricula() {
        return matricula;
    }

    public String getFuncao() {
        return funcao;
    }

}
