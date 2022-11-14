/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author lugar
 */
public class Pedido {

    private int id;
    private double valorTotal;
    private char estado; // S - aceito pelo cliente; A = aceito e agendado; F  = aceito e fechado; C  = cancelado
    private boolean agendado;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataEntrega;
    private String comentario;
    private List<Item> listaItens;

    public Pedido(int id, char estado, boolean agendado, List<Item> listaItens) {
        this.id = id;
        this.estado = estado;
        this.agendado = agendado;
        this.listaItens = listaItens;
        this.valorTotal = 0;
        for (Item item : listaItens) {
            this.valorTotal += item.getValorTotal();
        }
    }

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public char getEstado() {
        return estado;
    }

    public boolean isAgendado() {
        return agendado;
    }

    public List<Item> getListaItens() {
        return listaItens;
    }

}
