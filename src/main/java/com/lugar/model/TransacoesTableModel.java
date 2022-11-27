/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lugar.model;

import com.lugar.model.Transacao;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;

/**
 *
 * @author rodrigosoares
 */
public class TransacoesTableModel extends AbstractTableModel {

    private String colunas[] = {"#", "Descrição", "Data", "Valor"};
    private List<Transacao> listaTransacoes;

    private final int COLUNA_ID = 0;
    private final int COLUNA_DESCRICAO = 1;
    private final int COLUNA_DIAHORA = 2;
    private final int COLUNA_VALOR = 3;

    public TransacoesTableModel(List<Transacao> listaTransacoes) {
        this.listaTransacoes = listaTransacoes;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return listaTransacoes.size();
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
        Transacao transacao = this.listaTransacoes.get(rowIndex);
        switch (columnIndex) {
            case COLUNA_ID:
                return transacao.getId();
            case COLUNA_DESCRICAO:
                return transacao.getDescricao();
            case COLUNA_DIAHORA:
                return transacao.getDiaHoraFormatado();
            case COLUNA_VALOR:
                return transacao.getValorFormatado();
        }
        return null;
    }
}
