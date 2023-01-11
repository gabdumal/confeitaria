/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public class ExcecaoStringInvalido extends ExcecaoAtributo {

    public ExcecaoStringInvalido(String atributo, boolean preenchido) {
        super(atributo, preenchido ? "ERRO: atributo " + atributo
                + ", do tipo String, foi preenchido de forma invalida!"
                : "ERRO: atributo " + atributo + ", do tipo String, não foi"
                + " preenchido, e não pode ser vazio!");
    }

    public ExcecaoStringInvalido(String atributo, String mensagemExtra) {
        super(atributo, mensagemExtra, "ERRO: atributo " + atributo + ", do tipo String,"
                + " foi preenchido de forma invalida!");
    }

}
