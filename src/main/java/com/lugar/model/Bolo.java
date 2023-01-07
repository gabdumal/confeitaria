/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import java.util.List;

/**
 *
 * @author rodrigosoares
 */
public class Bolo extends ProdutoPersonalizado {

    private Forma forma;
    private Caracteristica cobertura;
    private List<Caracteristica> recheios;

    public Bolo(Forma forma, Caracteristica cobertura, List<Caracteristica> recheios, int id, String receita, String detalhe, Caracteristica cor) {
        super(id, receita, detalhe, cor);
        this.forma = forma;
        this.cobertura = cobertura;
        this.recheios = recheios;
    }

    public Forma getForma() {
        return forma;
    }

    public Caracteristica getCobertura() {
        return cobertura;
    }

    public List<Caracteristica> getRecheios() {
        return recheios;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public void setCobertura(Caracteristica cobertura) {
        this.cobertura = cobertura;
    }

    public void addRecheio(Caracteristica recheio) {
        this.recheios.add(recheio);
    }
}
