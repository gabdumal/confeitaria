/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

import com.lugar.confeitaria.Util;

/**
 *
 * @author lugar
 */
public class ExcecaoDataInvalida extends ExcecaoAtributo {

    public ExcecaoDataInvalida(String atributo) {
        super(atributo, "ERRO: a data informada (" + atributo + ") não segue os padrões " + Util.FORMATO_DATA + "!");
    }
    public ExcecaoDataInvalida(String atributo, String mensagemExtra) {
        super(atributo, mensagemExtra, "ERRO: atributo " + atributo + ", do tipo LocalDate, foi preenchido de forma invalida!");
    }
    
}
