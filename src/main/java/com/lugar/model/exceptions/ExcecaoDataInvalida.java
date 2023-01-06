/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoDataInvalida extends Exception {

    public ExcecaoDataInvalida() {
        super("ERRO: a data informada não segue os padrões dd/MM/yyyy!");
    }
}
