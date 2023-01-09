/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoIntegerInvalido extends Exception {

    public ExcecaoIntegerInvalido(String atributo, int inteiro) {
        super("ERRO: atributo " + atributo + " (" + inteiro + "), do tipo "
                + "inteiro, foi preenchido de forma invalida!");
    }

}
