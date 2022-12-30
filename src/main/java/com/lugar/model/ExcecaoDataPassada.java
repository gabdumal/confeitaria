/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class ExcecaoDataPassada extends Exception {

    public ExcecaoDataPassada() {
        super("ERRO: a data de entrega deve ser pelo menos um dia após a atual");
    }
}
