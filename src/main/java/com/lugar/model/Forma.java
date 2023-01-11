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
public class Forma extends Caracteristica {

    private int recheios;
    private double gramaRecheio;
    private double gramaCobertura;
    private double gramaMassa;

    public Forma(int id, String tipo, String nome, double valorGrama,
            int recheios, double gramaRecheio, double gramaCobertura, double gramaMassa) {
        super(id, tipo, nome, valorGrama);
        this.recheios = recheios;
        this.gramaRecheio = gramaRecheio;
        this.gramaCobertura = gramaCobertura;
        this.gramaMassa = gramaMassa;
    }

    public int getRecheios() {
        return recheios;
    }

    public double getGramaRecheio() {
        return gramaRecheio;
    }

    public double getGramaCobertura() {
        return gramaCobertura;
    }

    public double getGramaMassa() {
        return gramaMassa;
    }

}
