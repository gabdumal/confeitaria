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
public class Pedido extends Transacao {

    private char estado; //F: Finalizado; E: Pronto para entrega; S: Solicitado
    private LocalDateTime dataEntrega;
    private String comentario;
    private List<Item> listaItens;

    public Pedido(int id, char estado, LocalDateTime dataEntrega,
            String comentario, List<Item> listaItens) {
        super(id, Pedido.calculaValorTotal(listaItens), LocalDateTime.now(), "Pedido");
        this.estado = estado;
        this.dataEntrega = dataEntrega;
        this.comentario = comentario;
        this.listaItens = listaItens;
    }

    @Override
    public double getValor() {
        double valorTotal = 0;
        for (Item item : listaItens) {
            valorTotal += item.getValorTotal();
        }
        return valorTotal;
    }

    public List<Item> getListaItens() {
        return listaItens;
    }

    private static double calculaValorTotal(List<Item> listaItens) {
        double valorTotal = 0;
        for (Item item : listaItens) {
            valorTotal += item.getValorTotal();
        }
        return valorTotal;
    }

}
