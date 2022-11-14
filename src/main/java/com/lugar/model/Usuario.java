/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

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
    private String email;
    private String telefone;
    private String endereco;
    private String cartao;
    private String identificador;

    public Usuario(
            int id,
            String nomeUsuario,
            String senhaHash,
            boolean admin
    ) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senhaHash = senhaHash;
        this.admin = admin;
    }

    public Usuario(
            int id,
            String nome,
            String nomeUsuario,
            String senhaHash,
            boolean admin,
            String email,
            String telefone,
            String endereco,
            String cartao,
            String identificador
    ) {
        this.id = id;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senhaHash = senhaHash;
        this.admin = admin;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cartao = cartao;
        this.identificador = identificador;
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

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCartao() {
        return cartao;
    }

    public String getIdentificador() {
        return identificador;
    }

}
