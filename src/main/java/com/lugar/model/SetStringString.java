/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author devgabmal
 */
public class SetStringString {

    private String chave;
    private String texto;

    public SetStringString(String chave, String texto) {
        this.chave = chave;
        this.texto = texto;
    }

    public String getChave() {
        return chave;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return this.texto;
    }

}
