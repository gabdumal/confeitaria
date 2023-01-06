/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public abstract class Usuario {

    private int id;
    private String nome;
    private String nomeUsuario;
    private String senhaHash;
    private String identificador;
    private String email;
    private String telefone;
    private boolean admin;
    private Endereco endereco;

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
            String identificador,
            String email,
            String telefone,
            boolean admin,
            Endereco endereco
    ) {
        this(id, nomeUsuario, senhaHash, admin);
        this.nome = nome;
        this.identificador = identificador;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getIdentificador() {
        return identificador;
    }

    public abstract String getIdentificadorFormatado();

    public Endereco getEndereco() {
        return endereco;
    }

}
