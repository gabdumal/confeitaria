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
    private int IdEndereco;
    private String cartao;
    private String identificador;
    private Endereco endereco;

    public Cliente(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            boolean admin,
            String email,
            String telefone,
            int IdEndereco,
            String cartao,
            String identificador,
            Endereco endereco
    ) {
        super(idUsuario, nome, nomeUsuario, senhaHash, admin, email, telefone);
        this.idCliente = idUsuario;
        this.IdEndereco = IdEndereco;
        this.cartao = cartao;
        this.identificador = identificador;
        this.endereco = endereco;
    }

    public int getIdEndereco() {
        return IdEndereco;
    }

    public String getCartao() {
        return cartao;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

}
