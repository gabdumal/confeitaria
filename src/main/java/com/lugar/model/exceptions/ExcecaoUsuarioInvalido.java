/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoUsuarioInvalido extends Exception {

    public ExcecaoUsuarioInvalido(Throwable cause) {
        super("ERRO: o usuário não pôde ser instanciado pois foram "
                + "informados atributos inváidos!", cause);
    }

    public ExcecaoUsuarioInvalido(String message, Throwable cause) {
        super(message, cause);
    }

}
