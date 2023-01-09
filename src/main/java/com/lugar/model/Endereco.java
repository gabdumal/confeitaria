/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Endereco(String numero, String complemento, String logradouro, String bairro, String cidade, String uf, String cep) {
        this.numero = numero;
        this.complemento = complemento;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public Endereco(Endereco endereco) {
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.logradouro = endereco.getLogradouro();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
        this.cep = endereco.getCep();
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
