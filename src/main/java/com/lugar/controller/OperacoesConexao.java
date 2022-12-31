/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lugar.controller;

import java.util.List;

/**
 *
 * @author lugar
 */
public interface OperacoesConexao <T> {

    public List<T> buscaTodos();
    public T busca(int id);
    public int insere(T objeto);
    public int atualiza(T objeto);
    public int deleta(int id);
}
