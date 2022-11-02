/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

/**
 *
 * @author lugar
 */
public class Usuario {

    private int id;
    private String nome;
    private String nomeUsuario;
    private String senhaHash;
    private boolean admin;

    public Usuario(
            int idP,
            String nomeP,
            String nomeUsuarioP,
            String senhaHashP,
            boolean adminP
    ) {
        id = idP;
        nome = nomeP;
        nomeUsuario = nomeUsuarioP;
        senhaHash = senhaHashP;
        admin = adminP;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public boolean isAdmin() {
        return admin;
    }

}
