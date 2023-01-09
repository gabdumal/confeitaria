/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.exceptions;

/**
 *
 * @author lugar
 */
public class ExcecaoPessoaFisicaInvalida extends ExcecaoClienteInvalido {

    public ExcecaoPessoaFisicaInvalida(Throwable cause) {
        super("ERRO: o cliente (pessoa física) não pôde ser instanciado pois foram "
                + "informados atributos inváidos!", cause);
    }

}
