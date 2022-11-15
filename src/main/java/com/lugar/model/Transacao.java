/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import java.time.LocalDateTime;

/**
 *
 * @author rodrigosoares
 */
public class Transacao {

    private String id;
    private double valor;
    private LocalDateTime diaHora;
    private String descricao;

    public Transacao(String transacao, double valor, LocalDateTime diaHora, String descricao) {
        this.id = id;
        this.valor = valor;
        this.diaHora = diaHora;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDiaHora() {
        return diaHora;
    }

    public String getDescricao() {
        return descricao;
    }
}
