/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import com.lugar.model.exceptions.ExcecaoIntegerInvalido;
import com.lugar.model.exceptions.ExcecaoStringSensivelInvalido;
import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;
import java.text.ParseException;

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
    ) throws ExcecaoUsuarioInvalido {
        try {
            Usuario.verificaPreenchimento(id, nomeUsuario, senhaHash);
            this.id = id;
            this.nomeUsuario = nomeUsuario;
            this.senhaHash = senhaHash;
            this.admin = admin;
        } catch (Exception ex) {
            throw new ExcecaoUsuarioInvalido(ex);
        }
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
    ) throws ExcecaoUsuarioInvalido {
        this(id, nomeUsuario, senhaHash, admin);
        this.nome = nome;
        this.identificador = identificador;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public static void verificaPreenchimento(
            int id,
            String nomeUsuario,
            String senhaHash
    ) throws ExcecaoIntegerInvalido, ExcecaoStringSensivelInvalido {
        if (id < 0) {
            throw new ExcecaoIntegerInvalido("id");
        }
        if (nomeUsuario.isBlank() || nomeUsuario.contains(" ")) {
            throw new ExcecaoStringSensivelInvalido("nomeUsuario");
        }
        if (senhaHash.isBlank()) {
            throw new ExcecaoStringSensivelInvalido("senhaHash");
        }
    }

    public boolean verificaLogin(String nomeUsuario, String senhaHash) throws ExcecaoStringSensivelInvalido {
        try {
            Usuario.verificaPreenchimento(0, nomeUsuario, senhaHash);
            if (this.nomeUsuario.equals(nomeUsuario)
                    && this.senhaHash.equals(senhaHash)) {
                return true;
            }
        } catch (ExcecaoIntegerInvalido ex) {
        }
        return false;
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

    public String getTelefoneFormatado() {
        try {
            String mascara;
            if (this.telefone.length() == 11) {
                mascara = "(##) #####-####";
            } else if (this.telefone.length() == 10) {
                mascara = "(##) ####-####";
            } else {
                return this.telefone;
            }
            return Util.formataString(this.telefone, mascara);
        } catch (ParseException ex) {
            return this.telefone;
        }
    }

    public String getIdentificador() {
        return identificador;
    }

    public abstract String getIdentificadorFormatado();

    public Endereco getEndereco() {
        return endereco;
    }

}
