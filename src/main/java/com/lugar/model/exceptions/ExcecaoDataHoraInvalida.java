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
public class ExcecaoDataHoraInvalida extends Exception {

    public ExcecaoDataHoraInvalida() {
        super("ERRO: a data informada não segue os padrões " + Util.FORMATO_DATAHORA + "!");
    }
}
