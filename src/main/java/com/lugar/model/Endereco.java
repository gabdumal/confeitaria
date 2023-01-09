/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import com.lugar.model.exceptions.ExcecaoEnderecoInvalido;
import com.lugar.model.exceptions.ExcecaoStringInvalido;
import java.text.ParseException;

/**
 *
 * @author lugar
 */
public class Endereco {

    private int id;
    private String numero;
    private String complemento;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public Endereco(String numero, String complemento, String logradouro,
            String bairro, String cidade, String uf, String cep) throws ExcecaoEnderecoInvalido {
        try {
            Endereco.verificaPreenchimento(numero, logradouro, bairro, cidade, uf, cep);
            this.numero = numero;
            this.complemento = complemento;
            this.logradouro = logradouro;
            this.bairro = bairro;
            this.cidade = cidade;
            this.uf = uf;
            this.cep = cep;
        } catch (ExcecaoStringInvalido ex) {
            throw new ExcecaoEnderecoInvalido(ex);
        }
    }

    public static void verificaPreenchimento(
            String numero, String logradouro, String bairro, String cidade, String uf, String cep) throws ExcecaoStringInvalido {
        if (numero.isBlank()) {
            throw new ExcecaoStringInvalido("numero");
        }
        if (logradouro.isBlank()) {
            throw new ExcecaoStringInvalido("logradouro");
        }
        if (bairro.isBlank()) {
            throw new ExcecaoStringInvalido("bairro");
        }
        if (bairro.matches(".*\\d.*")) {
            throw new ExcecaoStringInvalido("bairro", "Bairro não pode conter números.");
        }
        if (cidade.isBlank()) {
            throw new ExcecaoStringInvalido("cidade");
        }
        if (cidade.matches(".*\\d.*")) {
            throw new ExcecaoStringInvalido("cidade", "Cidade não pode conter números.");
        }
        if (uf.isBlank()) {
            throw new ExcecaoStringInvalido("uf");
        }
        if (uf.matches(".*\\d.*")) {
            throw new ExcecaoStringInvalido("uf", "UF não pode conter números.");
        }
        if (uf.length() != 2) {
            throw new ExcecaoStringInvalido("uf", "UF deve ter dois caracteres.");
        }
        if (cep.isBlank()) {
            throw new ExcecaoStringInvalido("cep");
        }
        if (uf.length() != 8) {
            throw new ExcecaoStringInvalido("cep", "CEP deve ter oito caracteres.");
        }
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }

    public String getCepFormatado() {
        try {
            return Util.formataString(this.cep, "#####-###");
        } catch (ParseException ex) {
            return this.cep;
        }
    }

    public String getEnderecoFormatado() {
        String formatado = this.logradouro + ", " + this.numero;
        if (!this.complemento.isBlank()) {
            formatado += " " + this.complemento;
        }
        formatado += " - " + this.bairro + ", " + this.cidade + " - " + this.uf
                + ", " + this.getCepFormatado();
        return formatado;
    }

}
