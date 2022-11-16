/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
/*
*Anna Júlia de Almeida Lucas - 2021760029
*Celso Gabriel Dutra Almeida Malosto - 202176002
*Lucas Paiva dos Santos - 2021760026
*Rodrigo Soares de Assis - 202176027
 */
public class Item {

    private int id;
    private double valorTotal;
    private int quantidade;
    private Produto produto;

    public Item(int id, Produto produto) {
        this.id = id;
        this.quantidade = produto.getQuantidade();
        this.valorTotal = produto.getValor() * this.quantidade;
        this.produto = produto;
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
