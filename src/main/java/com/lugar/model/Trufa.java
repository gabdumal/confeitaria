/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author rodrigosoares
 */
public class Trufa extends ProdutoPersonalizado {

    private Caracteristica recheio;

    public Trufa(Caracteristica recheio, int id, String receita, String detalhe, Caracteristica cor) {
        super(id, receita, detalhe, cor);
        this.recheio = recheio;
    }

    public Caracteristica getRecheio() {
        return recheio;
    }

    public void setRecheio(Caracteristica recheio) {
        this.recheio = recheio;
    }
}
