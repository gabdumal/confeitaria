/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.confeitaria;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lugar
 */
public class ProdutosTableModel extends AbstractTableModel {

    private String colunas[] = {"#", "Produto", "Valor", "Estoque"};
    private ArrayList<Produto> listaProdutos;

    private final int COLUNA_ID = 0;
    private final int COLUNA_PRODUTO = 1;
    private final int COLUNA_VALOR = 2;
    private final int COLUNA_ESTOQUE = 3;

    public ProdutosTableModel(ArrayList<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return listaProdutos.size();
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
        switch (columnIndex) {
            case COLUNA_ID:
                return Integer.class;
            case COLUNA_PRODUTO:
                return String.class;
            case COLUNA_VALOR:
                return Double.class;
            case COLUNA_ESTOQUE:
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = this.listaProdutos.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_ID:
                return produto.getId();
            case COLUNA_PRODUTO:
                return produto.getNome();
            case COLUNA_VALOR:
                return produto.getValor();
            case COLUNA_ESTOQUE:
                return produto.getQuantidade();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Produto produto = this.listaProdutos.get(rowIndex);
        switch (columnIndex) {
            case COLUNA_ID:
                produto.setId((int) (aValue));
                break;
            case COLUNA_PRODUTO:
                produto.setNome(String.valueOf(aValue));
                break;
            case COLUNA_VALOR:
                produto.setValor((double) aValue);
                break;
            case COLUNA_ESTOQUE:
                produto.setQuantidade((int) aValue);
        }
        fireTableDataChanged();
    }

}
