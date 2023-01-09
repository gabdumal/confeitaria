/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.confeitaria.Util;
import com.lugar.model.exceptions.ExcecaoIntegerInvalido;
import com.lugar.model.exceptions.ExcecaoStringInvalido;
import com.lugar.model.exceptions.ExcecaoStringSensivelInvalido;
import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } catch (ExcecaoIntegerInvalido | ExcecaoStringSensivelInvalido ex) {
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
        try {
            this.verificaPreenchimento(id, nome, nomeUsuario, senhaHash, identificador, email, telefone);
            this.nome = nome;
            this.identificador = identificador;
            this.email = email;
            this.telefone = telefone;
            this.endereco = endereco;
        } catch (ExcecaoIntegerInvalido | ExcecaoStringSensivelInvalido | ExcecaoStringInvalido ex) {
            throw new ExcecaoUsuarioInvalido(ex);
        }
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

    public void verificaPreenchimento(
            int id,
            String nome,
            String nomeUsuario,
            String senhaHash,
            String identificador,
            String email,
            String telefone
    ) throws ExcecaoIntegerInvalido, ExcecaoStringSensivelInvalido, ExcecaoStringInvalido {
        Usuario.verificaPreenchimento(id, nomeUsuario, senhaHash);
        if (nome.isBlank()) {
            throw new ExcecaoStringInvalido("nome", false);
        }
        if (identificador.isBlank()) {
            throw new ExcecaoStringInvalido("identificador", false);
        }
        this.validaIdentificador(identificador);
        if (email.isBlank()) {
            throw new ExcecaoStringInvalido("email", false);
        }
        if (!email.contains("@") || !email.contains(".") || email.contains(" ")) {
            throw new ExcecaoStringInvalido("email", "E-mail deve conter [@] e [.], além de não poder ter espaços.");
        }
        if (telefone.isBlank()) {
            throw new ExcecaoStringInvalido("telefone", false);
        }
        if (!telefone.matches("[0-9]+") || telefone.contains(" ")) {
            throw new ExcecaoStringInvalido("telefone", "Telefone deve conter apenas números, além de não poder ter espaços.");
        }
        if (!(telefone.length() == 11 || telefone.length() == 10)) {
            throw new ExcecaoStringInvalido("telefone", "Telefone deve conter 11 ou 10 números, com o DDD.");
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

    public abstract boolean validaIdentificador(String identificador) throws ExcecaoStringInvalido;

}
