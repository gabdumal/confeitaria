/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class Caracteristica {

    private int id;
    private String tipo;
    private String nome;
    private double valorGrama;

    public Caracteristica(int id, String tipo, String nome, double valorGrama) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.valorGrama = valorGrama;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorGrama() {
        return valorGrama;
    }

    public void setValorGrama(double valorGrama) {
        this.valorGrama = valorGrama;
    }

}
