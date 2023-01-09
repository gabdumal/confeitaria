/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoClienteInvalido extends ExcecaoUsuarioInvalido {

    public ExcecaoClienteInvalido(Throwable cause) {
        super("ERRO: o cliente não pôde ser instanciado pois foram "
                + "informados atributos inváidos!", cause);
    }
    
    public ExcecaoClienteInvalido(String message, Throwable cause) {
    super(message, cause);
    }

}
