/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class Item {

    private int id;
    private double valorTotal;
    private Produto produto;
    private int quantidade;

    public Item(Produto produto) {
        this.id = -1;
        this.quantidade = produto.getCarrinho();
        this.valorTotal = produto.getValor() * this.quantidade;
        this.produto = produto;
    }

    public Item(int id, double valorTotal, Produto produto, int quantidade) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }
}
