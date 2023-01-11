/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import com.lugar.model.exceptions.ExcecaoDataInvalida;
import com.lugar.model.exceptions.ExcecaoPessoaFisicaInvalida;
import com.lugar.model.exceptions.ExcecaoStringInvalido;
import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;
import java.text.ParseException;
import java.time.LocalDate;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
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
        try {
            this.verificaPreenchimentoPessoaFisica(dataNascimento);
            this.dataNascimento = dataNascimento;
        } catch (ExcecaoDataInvalida ex) {
            throw new ExcecaoPessoaFisicaInvalida(ex);
        }
    }

    private void verificaPreenchimentoPessoaFisica(
            LocalDate dataNascimento
    ) throws ExcecaoDataInvalida {
        if (LocalDate.now().isBefore(dataNascimento)) {
            throw new ExcecaoDataInvalida("dataNascimento", "A data de nascimento não pode ser posterior ao dia de hoje.");
        }
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getDataNascimentoFormatada() {
        return Util.formataData(this.dataNascimento);
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
