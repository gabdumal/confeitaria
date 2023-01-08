/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Bolo;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.Trufa;
import java.util.List;

/**
 *
 * @author lugar
 */
public class OperacoesProdutoPersonalizado implements OperacoesConexao<ProdutoPersonalizado> {

    @Override
    public List<ProdutoPersonalizado> buscaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProdutoPersonalizado busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(ProdutoPersonalizado produtoPersonalizado) {
        int idProduto = Util.RETORNO_ERRO_INDETERMINADO;
        if (produtoPersonalizado instanceof Bolo) {
            OperacoesBolo operacoesBolo = new OperacoesBolo();
            operacoesBolo.insere((Bolo) produtoPersonalizado);
        } else {
            OperacoesTrufa operacoesTrufa = new OperacoesTrufa();
            operacoesTrufa.insere((Trufa) produtoPersonalizado);
        }
        return idProduto;
    }

    @Override
    public int atualiza(ProdutoPersonalizado objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
