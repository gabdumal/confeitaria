/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.data;

import com.lugar.confeitaria.Util;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.ProdutoPronto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lugar
 */
public class ProdutosTableModel extends AbstractTableModel {

    private String colunas[] = {"#", "Produto", "Valor", "Quantidade", "Total"};
    private List<Produto> listaProdutos;

    private final int COLUNA_ID = 0;
    private final int COLUNA_PRODUTO = 1;
    private final int COLUNA_VALOR = 2;
    private final int COLUNA_QUANTIDADE = 3;
    private final int COLUNA_TOTAL = 4;

    public ProdutosTableModel(List<Produto> listaProdutos) {
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
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = this.listaProdutos.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_ID:
                return produto.getId();
            case COLUNA_PRODUTO:
                if (produto instanceof ProdutoPronto) {
                    return ((ProdutoPronto) produto).getNome();
                } else {
                    return ((ProdutoPersonalizado) produto).getRecheio(0);
                }
            case COLUNA_VALOR:
                return produto.getValorFormatado();
            case COLUNA_QUANTIDADE:
                return produto.getCarrinho();
            case COLUNA_TOTAL:
                return Util.formataDinheiro(produto.getValor() * produto.getCarrinho());
        }
        return null;
    }

}
