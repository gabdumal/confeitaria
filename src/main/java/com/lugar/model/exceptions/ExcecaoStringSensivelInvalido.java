/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoStringSensivelInvalido extends Exception {

    public ExcecaoStringSensivelInvalido(String atributo) {
        super("ERRO: atributo sensível " + atributo + ", do tipo String,"
                + " foi preenchido de forma invalida!");
    }

}
