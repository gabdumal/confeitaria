/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

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

}
