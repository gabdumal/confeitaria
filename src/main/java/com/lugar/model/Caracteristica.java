/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
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

    @Override
    public String toString() {
        return this.nome;
    }

}
