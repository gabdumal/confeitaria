/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.lugar.confeitaria;

import java.util.ArrayList;

/**
 *
 * @author lugar
 */
public class Confeitaria {

    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        ArrayList<Usuario> listaUsuarios = conexao.buscaTodosUsuarios();

        Login login = new Login(listaUsuarios);
        login.setVisible(true);
    }
}
