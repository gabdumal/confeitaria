/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.controller;

import com.lugar.confeitaria.Util;
import com.lugar.model.Caracteristica;
import com.lugar.model.Forma;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lugar
 */
public class OperacoesCaracteristica implements OperacoesConexao<List<Caracteristica>> {

    @Override
    public List<List<Caracteristica>> buscaTodos() {
        String sql = "SELECT Caracteristica.id, Caracteristica.nome, "
                + "Caracteristica.tipo, Caracteristica.valorGrama, "
                + "Forma.recheios, Forma.gramaRecheio, forma.gramaCobertura, "
                + "Forma.gramaMassa FROM Caracteristica "
                + "LEFT JOIN Forma ON Caracteristica.id = Forma.id;";
        Connection conn = null;
        List<List<Caracteristica>> listaDeListasDeCaracteristicas = new ArrayList<List<Caracteristica>>();
        try {
            conn = Conexao.abreConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Caracteristica> listaFormas = new ArrayList<Caracteristica>();
            List<Caracteristica> listaCores = new ArrayList<Caracteristica>();
            List<Caracteristica> listaCoberturas = new ArrayList<Caracteristica>();
            List<Caracteristica> listaRecheios = new ArrayList<Caracteristica>();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Caracteristica caracteristica = null;
                if (tipo.equals(Util.CARACTERISTICA_FORMA)) {
                    caracteristica = new Forma(
                            rs.getInt("id"),
                            rs.getString("tipo"),
                            rs.getString("nome"),
                            rs.getDouble("valorGrama"),
                            rs.getInt("recheios"),
                            rs.getDouble("gramaRecheio"),
                            rs.getDouble("gramaCobertura"),
                            rs.getDouble("gramaMassa"));
                    listaFormas.add(caracteristica);
                } else {
                    caracteristica = new Caracteristica(
                            rs.getInt("id"),
                            rs.getString("tipo"),
                            rs.getString("nome"),
                            rs.getDouble("valorGrama"));
                    switch (tipo) {
                        case Util.CARACTERISTICA_COR:
                            listaCores.add(caracteristica);
                            break;
                        case Util.CARACTERISTICA_COBERTURA:
                            listaCoberturas.add(caracteristica);
                            break;
                        case Util.CARACTERISTICA_RECHEIO:
                            listaRecheios.add(caracteristica);
                            break;
                    }
                }
            }
            listaDeListasDeCaracteristicas.add(listaFormas);
            listaDeListasDeCaracteristicas.add(listaCores);
            listaDeListasDeCaracteristicas.add(listaCoberturas);
            listaDeListasDeCaracteristicas.add(listaRecheios);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.fechaConexao(conn);
        }
        return listaDeListasDeCaracteristicas;
    }

    @Override
    public List<Caracteristica> busca(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insere(List<Caracteristica> objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int atualiza(List<Caracteristica> objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
