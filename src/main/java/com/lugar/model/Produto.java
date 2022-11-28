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
public class Produto {

    private int id;
    private String nome;
    private double valor;
    private int quantidade;
    private boolean personalizado;

    public Produto(int id, String nome, double valor, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.personalizado = false;
    }

    public Produto(int id, String nome, double valor, int quantidade, boolean personalizado) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.personalizado = personalizado;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getValorFormatado() {
        return Util.formataDinheiro(this.valor);
    }

    public boolean isPersonalizado() {
        return personalizado;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        }
    }

    public void setNome(String nome) {
        if (!nome.isBlank()) {
            this.nome = nome;
        }
    }

    public void setValor(double valor) {
        if (valor >= 0) {
            this.valor = valor;
        }
    }

    public void setQuantidade(int quantidade) {
        if (quantidade >= 0) {
            this.quantidade = quantidade;
        }
    }

    public void setPersonalizado(boolean personalizado) {
        this.personalizado = personalizado;
    }
}
