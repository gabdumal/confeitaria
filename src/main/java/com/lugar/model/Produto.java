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
public abstract class Produto {

    private int id;
    private double valor;
    private int carrinho;

    public Produto(int id, double valor) {
        this.id = id;
        this.valor = valor;
        this.carrinho = 0;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return Util.formataDinheiro(this.valor);
    }

    public int getCarrinho() {
        return carrinho;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        }
    }

    public void setValor(double valor) {
        if (valor >= 0) {
            this.valor = valor;
        }
    }

    public void setCarrinho(int carrinho) {
        this.carrinho = carrinho;
    }

}
