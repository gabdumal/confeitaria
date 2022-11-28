/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class ProdutoPersonalizado extends Produto {

    private String recheio;
    private String cobertura;
    private String detalhe;

    public ProdutoPersonalizado(int id, String nome, double valor, int quantidade,
            String recheio, String cobertura, String detalhe) {
        super(id, nome, valor, quantidade);
        this.recheio = recheio;
        this.cobertura = cobertura;
        this.detalhe = detalhe;
    }

    public String getRecheio() {
        return recheio;
    }

    public String getCobertura() {
        return cobertura;
    }

    public String getDetalhe() {
        return detalhe;
    }

}
