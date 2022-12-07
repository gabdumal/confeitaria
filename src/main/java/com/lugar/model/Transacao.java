/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import java.time.LocalDateTime;

/**
 *
 * @author lugar
 */
public class Transacao {

    private int id;
    private double valor;
    private LocalDateTime diaHora;
    private String descricao;

    public Transacao(int id, double valor, LocalDateTime diaHora, String descricao) {
        this.id = id;
        this.valor = valor;
        this.diaHora = diaHora;
        this.descricao = descricao;
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

    public LocalDateTime getDiaHora() {
        return diaHora;
    }

    public String getDiaHoraString() {
        return diaHora.toString();
    }

    public String getDiaHoraFormatado() {
        return Util.formataDiaHora(this.diaHora);
    }

    public String getDescricao() {
        return descricao;
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

    public void setDiaHora(LocalDateTime diaHora) {
        this.diaHora = diaHora;
    }

    public void setDescricao(String descricao) {
        if (!descricao.isBlank()) {
            this.descricao = descricao;
        }
    }
}
