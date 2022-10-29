/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

import javax.swing.JFrame;

/**
 *
 * @author cgmal
 */
public class Login {

    private JFrame janelaLogin;
    private int largura;
    private int altura;

    public Login(int larguraP, int alturaP) {
        janelaLogin = new JFrame();
        largura = larguraP;
        altura = alturaP;
    }

    public void configuraGUI() {
        janelaLogin.setSize(largura, altura);
        janelaLogin.setTitle("Gerenciamento LUGAR - Login");
        janelaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaLogin.setVisible(true);
    }

}
