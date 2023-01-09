/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoStringInvalido extends Exception {

    public ExcecaoStringInvalido(String atributo, String texto) {
        super("ERRO: atributo " + atributo + " (" + texto + "), do tipo String,"
                + " foi preenchido de forma invalida!");
    }

}
