/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;

/**
 *
 * @author lugar
 */
public abstract class Cliente extends Usuario {

    private String cartao;
    private boolean fisica;

    public Cliente(int idUsuario, String nomeUsuario, String senhaHash, boolean fisica) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nomeUsuario, senhaHash, false);
        this.fisica = fisica;
    }

    public Cliente(
            int idUsuario,
            String nome,
            String nomeUsuario,
            String senhaHash,
            String identificador,
            String email,
            String telefone,
            Endereco endereco,
            String cartao,
            boolean fisica
    ) throws ExcecaoUsuarioInvalido {
        super(idUsuario, nome, nomeUsuario, senhaHash, identificador, email,
                telefone, false, endereco);
        this.cartao = cartao;
        this.fisica = fisica;
    }

    public String getCartao() {
        return cartao;
    }

}
