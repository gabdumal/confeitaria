/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public abstract class ExcecaoAtributo extends Exception {

    String atributo;
    String mensagemExtra;

    public ExcecaoAtributo(String atributo, String message) {
        super(message);
        this.atributo = atributo;
    }

    public ExcecaoAtributo(String atributo, String mensagemExtra, String message) {
        super(message + " " + mensagemExtra);
        this.atributo = atributo;
        this.mensagemExtra = mensagemExtra;
    }

    public String getAtributo() {
        return atributo;
    }

    public String getMensagemExtra() {
        return this.mensagemExtra;
    }

}
