/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public abstract class ProdutoPersonalizado extends Produto {

    private String receita;
    private String detalhe;
    private Caracteristica cor;

    public ProdutoPersonalizado(int id, String receita, String detalhe) {
        super(id);
        this.receita = receita;
        this.detalhe = detalhe;
    }

    public ProdutoPersonalizado(int id, String receita, String detalhe, Caracteristica cor) {
        super(id);
        this.receita = receita;
        this.detalhe = detalhe;
        this.cor = cor;
    }

    public Caracteristica getCor() {
        return cor;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String getReceita() {
        return receita;
    }

    public void setCor(Caracteristica cor) {
        this.cor = cor;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

}
