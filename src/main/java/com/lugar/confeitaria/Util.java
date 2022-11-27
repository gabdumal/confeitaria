/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author lugar
 */
public class Util {

    public static String formataDinheiro(double valor) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String valorFormatado = formatter.format(valor);
        return valorFormatado;
    }

    public static String formataDiaHora(LocalDateTime diaHora) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return diaHora.format(formatador);
    }

}
