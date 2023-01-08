/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;

/**
 *
 * @author lugar
 */
public class Trufa extends ProdutoPersonalizado {

    private Caracteristica recheio;

    public Trufa(int id, String detalhe) {
        super(id, Util.RECEITA_BOLO, detalhe);
    }

    public Trufa(int id, String detalhe, Caracteristica cor, Caracteristica recheio) {
        super(id, Util.RECEITA_TRUFA, detalhe, cor);
        this.recheio = recheio;
    }

    @Override
    public String getNome() {
        return "Trufa de " + this.recheio;
    }

    @Override
    public double getValor() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Caracteristica getRecheio() {
        return recheio;
    }

    public void setRecheio(Caracteristica recheio) {
        this.recheio = recheio;
    }

}
