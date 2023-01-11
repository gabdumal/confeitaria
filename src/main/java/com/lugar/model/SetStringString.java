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
