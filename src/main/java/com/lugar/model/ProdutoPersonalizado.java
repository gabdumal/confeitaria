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

    private String receita;
    private String detalhe;
    private Caracteristica forma;
    private Caracteristica cor;
    private Caracteristica cobertura;
    private List<Caracteristica> recheios;

    public ProdutoPersonalizado(int id, String receita, String detalhe) {
        super(id);
        this.recheios = new ArrayList<Caracteristica>();
        this.receita = receita;
        this.detalhe = detalhe;
    }

    public ProdutoPersonalizado(int id, String receita, String detalhe,
            Caracteristica forma, Caracteristica cor, Caracteristica cobertura,
            List<Caracteristica> recheios) {
        super(id);
        this.receita = receita;
        this.detalhe = detalhe;
        this.forma = forma;
        this.cor = cor;
        this.cobertura = cobertura;
        this.recheios = recheios;
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

    @Override
    public String getNome() {
        String nomeFormado = "";
        if (this.receita.equals("B")) {
            nomeFormado += "Bolo de ";
        } else {
            nomeFormado += "Trufa de ";
        }
        nomeFormado += this.getRecheio(0);
        return nomeFormado;
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
