/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.lugar.view.funcionario;

import com.lugar.view.VisualizacaoItemProdutoPersonalizado;
import com.lugar.view.VisualizacaoItemProdutoPronto;
import com.lugar.confeitaria.Util;
import com.lugar.controller.OperacoesPedido;
import com.lugar.model.Cliente;
import com.lugar.model.Item;
import com.lugar.model.Pedido;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.SetStringString;
import com.lugar.model.tables.ItensTableModel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author lugar
 */
public class EdicaoPedido extends javax.swing.JDialog {

    private int id;
    private java.awt.Frame pai;
    private OperacoesPedido operacoesPedido;
    private boolean ehPedido;
    private DefaultComboBoxModel modeloEstado;
    private ItensTableModel modeloTabela;
    private List<Item> listaItens;

    public EdicaoPedido(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public EdicaoPedido(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        this.id = id;
        this.pai = parent;
        this.operacoesPedido = new OperacoesPedido();
        Pedido pedido = operacoesPedido.busca(id);
        Cliente cliente = pedido.getCliente();
        this.modeloTabela = new ItensTableModel(pedido.getListaItens());
        this.carregaComboBox();
        initComponents();

        // Pedido
        textoValorPreenchido.setText(pedido.getValorFormatado());
        textoSolicitadoPreenchido.setText(pedido.getDiaHoraFormatado());
        textoEntregaPreenchido.setText(pedido.getDataEntregaFormatada());
        painelTextoComentario.setText(pedido.getComentario());
        String estado = pedido.getEstado();
        int indice = 0;
        if (estado.equals("E")) {
            indice = 1;
        } else if (estado.equals("F")) {
            indice = 2;
        }
        this.comboBoxEstado.setSelectedIndex(indice);

        // Cliente
        textoNomePreenchido.setText(cliente.getNome());
        textoTelefonePreenchido.setText(cliente.getTelefoneFormatado());
        textoEmailPreenchido.setText(cliente.getEmail());
        textoIdentificadorPreenchido.setText(cliente.getIdentificadorFormatado());
        painelTextoEndereco.setText(cliente.getEndereco().getEnderecoFormatado());

        tabelaItens.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable tabela = (JTable) mouseEvent.getSource();
                Point ponto = mouseEvent.getPoint();
                int linha = tabela.rowAtPoint(ponto);
                // Clique duplo
                if (mouseEvent.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                    Item itemClicado = (Item) modeloTabela.getValueAt(linha, 0);
                    chamaTelaVisualizacaoItem(itemClicado);
                }
            }
        });
    }

    private ItensTableModel getModeloTabela() {
        return this.modeloTabela;
    }

    private void editaPedido() {
        String estadoForm = ((SetStringString) comboBoxEstado.getSelectedItem()).getChave();

        if ((!estadoForm.isBlank())) {
            boolean confirmacao = JOptionPane.showConfirmDialog(null, "Deseja mudar o estado deste pedido?", "Atualização de pedido", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
            if (confirmacao) {
                int resultado = Util.RETORNO_SUCESSO;
                Pedido novoPedido = new Pedido(this.id, estadoForm);
                resultado = this.operacoesPedido.atualiza(novoPedido);
                if (resultado == Util.RETORNO_SUCESSO) {
                    JOptionPane.showMessageDialog(this.pai, "Atualização realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this.pai, "Infelizmente não foi possivel atualizar, tente novamente mais tarde!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void carregaComboBox() {
        this.modeloEstado = new DefaultComboBoxModel();
        SetStringString estadoSolicitado = new SetStringString("S", "Solicitado");
        SetStringString estadoPronto = new SetStringString("E", "Pronto p/ entrega");
        SetStringString estadoFinalizado = new SetStringString("F", "Finalizado");
        this.modeloEstado.addElement(estadoSolicitado);
        this.modeloEstado.addElement(estadoPronto);
        this.modeloEstado.addElement(estadoFinalizado);
    }

    private void chamaTelaVisualizacaoItem(Item itemClicado) {
        Produto produtoClicado = itemClicado.getProduto();
        if (produtoClicado instanceof ProdutoPronto) {
            VisualizacaoItemProdutoPronto visualizacaoItemProdutoPronto = new VisualizacaoItemProdutoPronto(this.pai, true, itemClicado);
            visualizacaoItemProdutoPronto.setLocationRelativeTo(null);
            visualizacaoItemProdutoPronto.setVisible(true);
        } else {
            VisualizacaoItemProdutoPersonalizado visualizacaoItemProdutoPersonalizado = new VisualizacaoItemProdutoPersonalizado(this.pai, true, itemClicado);
            visualizacaoItemProdutoPersonalizado.setLocationRelativeTo(null);
            visualizacaoItemProdutoPersonalizado.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        painelFormulario = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        painelCampos = new javax.swing.JPanel();
        textoValor = new javax.swing.JLabel();
        textoValorPreenchido = new javax.swing.JLabel();
        textoSolicitado = new javax.swing.JLabel();
        textoSolicitadoPreenchido = new javax.swing.JLabel();
        textoEntrega = new javax.swing.JLabel();
        textoEntregaPreenchido = new javax.swing.JLabel();
        textoEstado = new javax.swing.JLabel();
        comboBoxEstado = new javax.swing.JComboBox<>();
        textoComentario = new javax.swing.JLabel();
        painelPainelTextoComentario = new javax.swing.JScrollPane();
        painelTextoComentario = new javax.swing.JTextPane();
        painelCliente = new javax.swing.JPanel();
        textoNome = new javax.swing.JLabel();
        textoNomePreenchido = new javax.swing.JLabel();
        textoEmail = new javax.swing.JLabel();
        textoEmailPreenchido = new javax.swing.JLabel();
        textoTelefone = new javax.swing.JLabel();
        textoTelefonePreenchido = new javax.swing.JLabel();
        textoEndereco = new javax.swing.JLabel();
        textoIdentificador = new javax.swing.JLabel();
        textoIdentificadorPreenchido = new javax.swing.JLabel();
        painelPainelTextoEndereco = new javax.swing.JScrollPane();
        painelTextoEndereco = new javax.swing.JTextPane();
        painelItens = new javax.swing.JPanel();
        painelRolavelTabelaItens = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();
        painelBotoes = new javax.swing.JPanel();
        botaoVoltar = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edição do Pedido");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        painelFormulario.setLayout(new java.awt.GridBagLayout());

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Pedido");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        painelFormulario.add(titulo, gridBagConstraints);

        painelCampos.setMinimumSize(new java.awt.Dimension(187, 200));
        painelCampos.setPreferredSize(new java.awt.Dimension(300, 260));
        painelCampos.setLayout(new java.awt.GridBagLayout());

        textoValor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoValor.setText("Valor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoValor, gridBagConstraints);

        textoValorPreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoValorPreenchido, gridBagConstraints);

        textoSolicitado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoSolicitado.setText("Solicitado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoSolicitado, gridBagConstraints);

        textoSolicitadoPreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoSolicitadoPreenchido, gridBagConstraints);

        textoEntrega.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoEntrega.setText("Entrega:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoEntrega, gridBagConstraints);

        textoEntregaPreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoEntregaPreenchido, gridBagConstraints);

        textoEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoEstado.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoEstado, gridBagConstraints);

        comboBoxEstado.setModel(this.modeloEstado);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(comboBoxEstado, gridBagConstraints);

        textoComentario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoComentario.setText("Comentário:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        painelCampos.add(textoComentario, gridBagConstraints);

        painelPainelTextoComentario.setMinimumSize(new java.awt.Dimension(55, 70));
        painelPainelTextoComentario.setPreferredSize(new java.awt.Dimension(55, 70));

        painelTextoComentario.setEditable(false);
        painelTextoComentario.setMinimumSize(new java.awt.Dimension(55, 70));
        painelTextoComentario.setPreferredSize(new java.awt.Dimension(55, 70));
        painelPainelTextoComentario.setViewportView(painelTextoComentario);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        painelCampos.add(painelPainelTextoComentario, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        painelFormulario.add(painelCampos, gridBagConstraints);

        painelCliente.setPreferredSize(new java.awt.Dimension(339, 240));
        painelCliente.setLayout(new java.awt.GridBagLayout());

        textoNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoNome.setText("Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCliente.add(textoNome, gridBagConstraints);

        textoNomePreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCliente.add(textoNomePreenchido, gridBagConstraints);

        textoEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoEmail.setText("E-mail:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCliente.add(textoEmail, gridBagConstraints);

        textoEmailPreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCliente.add(textoEmailPreenchido, gridBagConstraints);

        textoTelefone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoTelefone.setText("Telefone:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCliente.add(textoTelefone, gridBagConstraints);

        textoTelefonePreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCliente.add(textoTelefonePreenchido, gridBagConstraints);

        textoEndereco.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoEndereco.setText("Endereço:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        painelCliente.add(textoEndereco, gridBagConstraints);

        textoIdentificador.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoIdentificador.setText("Identificador:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCliente.add(textoIdentificador, gridBagConstraints);

        textoIdentificadorPreenchido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCliente.add(textoIdentificadorPreenchido, gridBagConstraints);

        painelTextoEndereco.setEditable(false);
        painelTextoEndereco.setMinimumSize(new java.awt.Dimension(62, 66));
        painelTextoEndereco.setPreferredSize(new java.awt.Dimension(62, 66));
        painelPainelTextoEndereco.setViewportView(painelTextoEndereco);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        painelCliente.add(painelPainelTextoEndereco, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 0);
        painelFormulario.add(painelCliente, gridBagConstraints);

        painelItens.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        painelRolavelTabelaItens.setMinimumSize(new java.awt.Dimension(16, 100));
        painelRolavelTabelaItens.setPreferredSize(new java.awt.Dimension(452, 200));

        tabelaItens.setModel(this.getModeloTabela());
        tabelaItens.setMinimumSize(new java.awt.Dimension(0, 66));
        tabelaItens.removeColumn(tabelaItens.getColumnModel().getColumn(0));
        painelRolavelTabelaItens.setViewportView(tabelaItens);

        painelItens.add(painelRolavelTabelaItens);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        painelFormulario.add(painelItens, gridBagConstraints);

        painelBotoes.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        botaoVoltar.setText("Voltar");
        botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVoltarActionPerformed(evt);
            }
        });
        painelBotoes.add(botaoVoltar);

        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        painelBotoes.add(botaoEditar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        painelFormulario.add(painelBotoes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(painelFormulario, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        this.editaPedido();
        this.dispose();
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoVoltarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EdicaoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EdicaoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EdicaoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EdicaoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EdicaoPedido dialog = new EdicaoPedido(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JComboBox<String> comboBoxEstado;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelCampos;
    private javax.swing.JPanel painelCliente;
    private javax.swing.JPanel painelFormulario;
    private javax.swing.JPanel painelItens;
    private javax.swing.JScrollPane painelPainelTextoComentario;
    private javax.swing.JScrollPane painelPainelTextoEndereco;
    private javax.swing.JScrollPane painelRolavelTabelaItens;
    private javax.swing.JTextPane painelTextoComentario;
    private javax.swing.JTextPane painelTextoEndereco;
    private javax.swing.JTable tabelaItens;
    private javax.swing.JLabel textoComentario;
    private javax.swing.JLabel textoEmail;
    private javax.swing.JLabel textoEmailPreenchido;
    private javax.swing.JLabel textoEndereco;
    private javax.swing.JLabel textoEntrega;
    private javax.swing.JLabel textoEntregaPreenchido;
    private javax.swing.JLabel textoEstado;
    private javax.swing.JLabel textoIdentificador;
    private javax.swing.JLabel textoIdentificadorPreenchido;
    private javax.swing.JLabel textoNome;
    private javax.swing.JLabel textoNomePreenchido;
    private javax.swing.JLabel textoSolicitado;
    private javax.swing.JLabel textoSolicitadoPreenchido;
    private javax.swing.JLabel textoTelefone;
    private javax.swing.JLabel textoTelefonePreenchido;
    private javax.swing.JLabel textoValor;
    private javax.swing.JLabel textoValorPreenchido;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
