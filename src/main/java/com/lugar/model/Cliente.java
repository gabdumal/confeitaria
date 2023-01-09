/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.model.exceptions.ExcecaoClienteInvalido;
import com.lugar.model.exceptions.ExcecaoStringInvalido;
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
        try {
            this.verificaPreenchimentoCliente(cartao);
            this.cartao = cartao;
            this.fisica = fisica;
        } catch (ExcecaoStringInvalido ex) {
            throw new ExcecaoClienteInvalido(ex);
        }
    }

    private void verificaPreenchimentoCliente(
            String cartao
    ) throws ExcecaoStringInvalido {
        if (cartao.isBlank()) {
            throw new ExcecaoStringInvalido("cartao", false);
        }
        if (!cartao.matches("[0-9]+") || cartao.contains(" ")) {
            throw new ExcecaoStringInvalido("cartao", "Cartão deve conter apenas números, além de não poder ter espaços.");
        }
        if (cartao.length() != 16) {
            throw new ExcecaoStringInvalido("cartao", "Cartão deve conter 16 números.");
        }
    }

    public String getCartao() {
        return cartao;
    }

}
