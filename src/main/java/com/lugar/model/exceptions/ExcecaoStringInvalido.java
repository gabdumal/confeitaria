/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoStringInvalido extends ExcecaoAtributo {

    public ExcecaoStringInvalido(String atributo) {
        super(atributo, "ERRO: atributo " + atributo + ", do tipo String,"
                + " não foi preenchido, e não pode ser vazio!");
    }

    public ExcecaoStringInvalido(String atributo, String mensagemExtra) {
        super(atributo, mensagemExtra, "ERRO: atributo " + atributo + ", do tipo String,"
                + " foi preenchido de forma invalida!");
    }

}
