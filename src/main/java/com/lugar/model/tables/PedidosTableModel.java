/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model.tables;

import com.lugar.model.Pedido;
import com.lugar.model.Transacao;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lugar
 */
public class PedidosTableModel extends AbstractTableModel {

    private String colunas[] = {"#", "Solicitado", "Valor", "Entrega", "Status"};
    private List<Pedido> listaPedidos;

    private final int COLUNA_ID = 0;
    private final int COLUNA_SOLICITADO = 1;
    private final int COLUNA_VALOR = 2;
    private final int COLUNA_ENTREGA = 3;
    private final int COLUNA_ESTADO = 4;

    public PedidosTableModel(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return listaPedidos.size();
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
        Pedido pedido = this.listaPedidos.get(rowIndex);
        switch (columnIndex) {
            case COLUNA_ID:
                return pedido.getId();
            case COLUNA_SOLICITADO:
                return pedido.getDiaHoraFormatado();
            case COLUNA_VALOR:
                return ((Transacao) pedido).getValorFormatado();
            case COLUNA_ENTREGA:
                return pedido.getDataEntregaFormatada();
            case COLUNA_ESTADO:
                return pedido.getEstadoFormatado();
        }
        return null;
    }
}
