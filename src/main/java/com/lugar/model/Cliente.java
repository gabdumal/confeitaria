/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

/**
 *
 * @author lugar
 */
public class Cliente extends Usuario {

    private int idCliente;
    private String email;
    private String telefone;
    private String endereco;
    private String cartao;
    private String identificador;

    public Cliente(
            int idUsuario,
            String nomeUsuario,
            String senhaHash,
            boolean admin
    ) {
        super(idUsuario, nomeUsuario, senhaHash, admin);
    }

    public Cliente(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            boolean admin,
            int idCliente,
            String email,
            String telefone,
            String endereco,
            String cartao,
            String identificador
    ) {
        super(idUsuario, nome, nomeUsuario, senhaHash, admin);
        this.idCliente = idCliente;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cartao = cartao;
        this.identificador = identificador;
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
