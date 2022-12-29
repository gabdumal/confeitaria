/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class ExcecaoCampoInvalido extends Exception{

    public ExcecaoCampoInvalido(String campo) {
        super("ERRO: campo " + campo + " foi preenchido de forma invalida!");
    }
    
}
