/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author cgmal
 */
public class Login {

    private JFrame janelaLogin;
    private int largura;
    private int altura;
    private JButton botaoLogin;
    private JButton botaoCadastro;
    private JLabel textoUsuario;
    private JTextField campoUsuario;
    private JLabel textoSenha;
    private JTextField campoSenha;

    public Login(int larguraP, int alturaP) {
        janelaLogin = new JFrame();
        largura = larguraP;
        altura = alturaP;
        textoUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField(25);
        textoSenha = new JLabel("Senha:");
        campoSenha = new JTextField(25);
        botaoLogin = new JButton("Entrar");
        botaoCadastro = new JButton("Registrar-se");
    }

    public void configuraGUI() {
        Container container = janelaLogin.getContentPane();
        FlowLayout leiaute = new FlowLayout();
        container.setLayout(leiaute);

        janelaLogin.setSize(largura, altura);
        janelaLogin.setTitle("Gerenciamento LUGAR - Login");

        container.add(textoUsuario);
        container.add(campoUsuario);
        container.add(textoSenha);
        container.add(campoSenha);
        container.add(botaoLogin);
        container.add(botaoCadastro);

        janelaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaLogin.setVisible(true);
    }

    public void configuraOuvintesBotao() {
        ActionListener ouvinteBotao = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object origem = ae.getSource();
                if (origem == botaoLogin) {
                    System.out.println("Entrar!");
                } else if (origem == botaoCadastro) {
                    System.out.println("Registrar-se!");
                }
            }
        };

        botaoLogin.addActionListener(ouvinteBotao);
        botaoCadastro.addActionListener(ouvinteBotao);

    }
}
