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

    // Constantes
    public final static int RETORNO_SUCESSO = 0;
    public final static int RETORNO_ERRO_INDETERMINADO = -1;
    public final static int RETORNO_ERRO_NAO_UNICO = -2;
    public final static int TIPO_PRONTO = 0;
    public final static int TIPO_PERSONALIZADO = 1;
    public final static String RECEITA_BOLO = "B";
    public final static String RECEITA_TRUFA = "T";
    public final static String CARACTERISTICA_FORMA = "F";
    public final static String CARACTERISTICA_COR = "C";
    public final static String CARACTERISTICA_COBERTURA = "T";
    public final static String CARACTERISTICA_RECHEIO = "R";

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
