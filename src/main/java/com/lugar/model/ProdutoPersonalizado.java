/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lugar
 */
public class ProdutoPersonalizado extends Produto {

    private Caracteristica forma;
    private List<Caracteristica> recheios;
    private Caracteristica cobertura;
    private Caracteristica cor;
    private String receita;
    private String detalhe;

    public ProdutoPersonalizado(int id, String receita, String detalhe) {
        super(id);
        this.recheios = new ArrayList<Caracteristica>();
        this.receita = receita;
        this.detalhe = detalhe;
    }

    public Caracteristica getForma() {
        return forma;
    }

    public Caracteristica getRecheio(int indice) {
        return recheios.get(indice);
    }

    public List<Caracteristica> getRecheios() {
        return recheios;
    }

    public Caracteristica getCobertura() {
        return cobertura;
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

    @Override
    public double getValor() {
        // todo
        return 0;
    }

    public void setForma(Caracteristica forma) {
        this.forma = forma;
    }

    public void addRecheio(Caracteristica recheio) {
        this.recheios.add(recheio);
    }

    public void setCobertura(Caracteristica cobertura) {
        this.cobertura = cobertura;
    }

    public void setCor(Caracteristica cor) {
        this.cor = cor;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

}
