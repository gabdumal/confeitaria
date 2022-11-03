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
            int id,
            String nome,
            String nomeUsuario,
            String senhaHash,
            boolean admin
    ) {
        this.id = id;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senhaHash = senhaHash;
        this.admin = admin;
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
