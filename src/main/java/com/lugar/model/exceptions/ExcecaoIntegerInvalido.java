/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoIntegerInvalido extends ExcecaoAtributo {

    public ExcecaoIntegerInvalido(String atributo) {
        super(atributo, "ERRO: atributo " + atributo + ", do tipo "
                + "inteiro, foi preenchido de forma invalida!");
    }

}
