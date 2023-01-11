/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public class Trufa extends ProdutoPersonalizado {

    private Caracteristica recheio;

    public Trufa(int id, String detalhe) {
        super(id, Util.RECEITA_TRUFA, detalhe);
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
        return this.getCor().getValorGrama() * 0.025 + this.getRecheio().getValorGrama() * 40;
    }

    public Caracteristica getRecheio() {
        return recheio;
    }

    public void setRecheio(Caracteristica recheio) {
        this.recheio = recheio;
    }

}
