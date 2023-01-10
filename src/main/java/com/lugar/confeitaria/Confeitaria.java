/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.lugar.confeitaria;

import com.lugar.controller.Conexao;
import com.lugar.view.Login;

/**
 *
 * @author lugar
 */
public class Confeitaria {

    public static void main(String[] args) {
        Conexao.criaBancoDeDados();

        Login login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }
}
