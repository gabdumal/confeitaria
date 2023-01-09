/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

import com.lugar.model.exceptions.ExcecaoDataHoraInvalida;
import com.lugar.model.exceptions.ExcecaoDataInvalida;
import com.lugar.model.exceptions.ExcecaoDataPassada;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.text.MaskFormatter;

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
    public final static String FORMATO_DATA = "dd/MM/yyyy";
    public final static String FORMATO_HORA = "HH:mm:ss";
    public final static String FORMATO_DATAHORA = "dd/MM/yyyy HH:mm:ss";

    public static String formataDinheiro(double valor) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String valorFormatado = formatter.format(valor);
        return valorFormatado;
    }

    public static String formataDiaHora(LocalDateTime diaHora) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern(Util.FORMATO_DATAHORA);
        return diaHora.format(formatador);
    }

    private static MaskFormatter criaFormatador(String mascara) {
        MaskFormatter formatador = null;
        try {
            formatador = new MaskFormatter(mascara);
            formatador.setPlaceholderCharacter('_');
            formatador.setValueContainsLiteralCharacters(false);
            formatador.setAllowsInvalid(false);
            formatador.setOverwriteMode(true);
        } catch (ParseException ex) {
            System.err.println("Formatador é inválido: " + ex.getMessage());
        }
        return formatador;
    }

    public static String formataString(String texto, String mascara) throws ParseException {
        MaskFormatter formatador = Util.criaFormatador(mascara);
        return formatador.valueToString(texto);
    }

    public static LocalDate converteData(String data) throws ExcecaoDataInvalida {
        if (Util.dataValida(data)) {
            return LocalDate.parse(data, DateTimeFormatter.ofPattern(Util.FORMATO_DATA));
        } else {
            throw new ExcecaoDataInvalida("data");
        }
    }

    public static LocalDateTime converteDataHora(String dataHora) throws ExcecaoDataHoraInvalida {
        if (Util.dataValida(dataHora)) {
            return LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern(Util.FORMATO_DATAHORA));
        } else {
            throw new ExcecaoDataHoraInvalida();
        }
    }

    public static boolean dataValida(String data) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Util.FORMATO_DATA);
            format.setLenient(false);
            format.parse(data);
        } catch (ParseException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean horaValida(String hora) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Util.FORMATO_HORA);
            format.setLenient(false);
            format.parse(hora);
        } catch (ParseException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean dataHoraValida(String dataHora) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Util.FORMATO_DATAHORA);
            format.setLenient(false);
            format.parse(dataHora);

        } catch (ParseException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean dataPassada(String dataEntrega) {
        String formatString = "dd/MM/yyyy";
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            LocalDateTime hoje = LocalDateTime.now();
            String hojeFormatado = formataDiaHora(hoje);
            Date date1 = format.parse(hojeFormatado);
            Date date2 = format.parse(dataEntrega);
            if (date1.equals(date2)) {
                return false;
            } else if (date2.before(date1)) {
                return false;
            }
        } catch (ParseException | IllegalArgumentException ex) {
            return false;
        }
        return true;
    }
}
