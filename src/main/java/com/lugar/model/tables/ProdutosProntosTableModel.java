/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.tables;

import com.lugar.model.ProdutoPronto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public class ProdutosProntosTableModel extends AbstractTableModel {

    private String colunas[] = {"#", "Produto", "Valor", "Estoque"};
    private List<ProdutoPronto> listaProdutos;

    private final int COLUNA_ID = 0;
    private final int COLUNA_PRODUTO = 1;
    private final int COLUNA_VALOR = 2;
    private final int COLUNA_ESTOQUE = 3;

    public ProdutosProntosTableModel(List<ProdutoPronto> listaProdutos) {
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
        ProdutoPronto produto = this.listaProdutos.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_ID:
                return produto.getId();
            case COLUNA_PRODUTO:
                return produto.getNome();
            case COLUNA_VALOR:
                return produto.getValorFormatado();
            case COLUNA_ESTOQUE:
                return produto.getEstoque();
        }
        return null;
    }

}
