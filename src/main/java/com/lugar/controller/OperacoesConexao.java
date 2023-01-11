/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lugar.controller;

import java.util.List;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public interface OperacoesConexao <T> {

    public List<T> buscaTodos();
    public T busca(int id);
    public int insere(T objeto);
    public int atualiza(T objeto);
    public int deleta(int id);
}
