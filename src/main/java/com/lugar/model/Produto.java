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
    private int carrinho;

    public Produto(int id) {
        this.id = id;
        this.carrinho = 0;
    }

    public Produto(int id, int carrinho) {
        this.id = id;
        this.carrinho = carrinho;
    }

    public int getId() {
        return id;
    }

    public abstract double getValor();

    public String getValorFormatado() {
        return Util.formataDinheiro(this.getValor());
    }

    public int getCarrinho() {
        return carrinho;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        }
    }

    public void setCarrinho(int carrinho) {
        this.carrinho = carrinho;
    }

    public abstract String getNome();

}
