/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.lugar.confeitaria;

import com.lugar.controller.Conexao;
import com.lugar.model.Usuario;
import com.lugar.view.Login;
import java.util.List;

/**
 *
 * @author lugar
 */
public class Confeitaria {

    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        List<Usuario> listaUsuarios = conexao.buscaTodosUsuarios();

        Login login = new Login(listaUsuarios);
        login.setVisible(true);
    }
}
