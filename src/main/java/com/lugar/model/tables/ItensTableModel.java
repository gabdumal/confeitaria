/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.tables;

import com.lugar.confeitaria.Util;
import com.lugar.model.Item;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.ProdutoPronto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lugar
 */
public class ItensTableModel extends AbstractTableModel {

    private String colunas[] = {"#", "Item", "Valor", "Quantidade", "Total"};
    private List<Item> listaItens;

    private final int COLUNA_ITEM = 0;
    private final int COLUNA_NOME = 1;
    private final int COLUNA_VALOR = 2;
    private final int COLUNA_QUANTIDADE = 3;
    private final int COLUNA_TOTAL = 4;

    public ItensTableModel(List<Item> listaItens) {
        this.listaItens = listaItens;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return listaItens.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int indice) {
        return colunas[indice];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = this.listaItens.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_ITEM:
                return item;
            case COLUNA_NOME:
                Produto produto = item.getProduto();
                return produto.getNome();
            case COLUNA_VALOR:
                return Util.formataDinheiro(item.getValorTotal() / item.getQuantidade());
            case COLUNA_QUANTIDADE:
                return item.getQuantidade();
            case COLUNA_TOTAL:
                return Util.formataDinheiro(item.getValorTotal());
        }

        return null;
    }

}
