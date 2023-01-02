/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoNovoEstoqueInvalido extends Exception {

    public ExcecaoNovoEstoqueInvalido() {
        super("ERRO: não é possível inserir um item pois não há estoque disponível do produto associado!");
    }

}
