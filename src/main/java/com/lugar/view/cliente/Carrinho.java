/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.lugar.view.cliente;

import com.lugar.confeitaria.Util;
import com.lugar.controller.OperacoesPedido;
import com.lugar.controller.OperacoesProduto;
import com.lugar.model.Item;
import com.lugar.model.Pedido;
import com.lugar.model.Produto;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.data.ProdutosTableModel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author lugar
 */
public class Carrinho extends javax.swing.JDialog {

    private int idUsuario;
    private List<Produto> listaProdutos;
    private ProdutosTableModel modeloTabela;
    private Map<Integer, Integer> listaProdutosCarrinho;
    private java.awt.Frame pai;

    public Carrinho(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.pai = parent;
        initComponents();
    }

    public Carrinho(java.awt.Frame parent, boolean modal, Map<Integer, Integer> listaProdutosCarrinho) {
        super(parent, modal);

        this.listaProdutosCarrinho = listaProdutosCarrinho;
        this.montaListaProdutos();

        initComponents();

        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable tabela = (JTable) mouseEvent.getSource();
                Point ponto = mouseEvent.getPoint();
                int linha = tabela.rowAtPoint(ponto);
                // Clique duplo
                if (mouseEvent.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                    chamaTelaEdicaoProdutoCarrinho((int) modeloTabela.getValueAt(linha, 0));
                }
            }
        });
    }

    public Map<Integer, Integer> getListaProdutosCarrinho() {
        return this.listaProdutosCarrinho;
    }

    private ProdutosTableModel getModeloTabela() {
        this.atualizaModeloTabela();
        return this.modeloTabela;
    }

    // Atualiza dados
    private void montaListaProdutos() {
        this.listaProdutos = new ArrayList<Produto>();
        for (int id : listaProdutosCarrinho.keySet()) {
            OperacoesProduto novoProduto = new OperacoesProduto();
            Produto produto = novoProduto.busca(id);
            int quantidadeCarrinho = listaProdutosCarrinho.get(id);
            if (produto instanceof ProdutoPersonalizado
                    || (produto instanceof ProdutoPronto && quantidadeCarrinho > 0)) {
                produto.setCarrinho(quantidadeCarrinho);
                this.listaProdutos.add(produto);
            }
        }
    }

    private void atualizaModeloTabela() {
        // Atualiza lista de produtos
        this.montaListaProdutos();
//        for (Produto produto : listaProdutos) {
//            produto.setCarrinho(listaProdutosCarrinho.get(produto.getId()));
//        }
        this.modeloTabela = new ProdutosTableModel(this.listaProdutos);
    }

    private void atualizaTabela() {
        this.atualizaModeloTabela();
        tabelaProdutos.setModel(this.modeloTabela);
        tabelaProdutos.removeColumn(tabelaProdutos.getColumnModel().getColumn(0));
    }

    // Ações
    private void limparCarrinho() {
        for (int id : listaProdutosCarrinho.keySet()) {
            listaProdutosCarrinho.put(id, 0);
        }
        this.atualizaTabela();
    }

    private void fecharPedido() {
        if (this.listaProdutos.size() == 0) {
            return;
        }

        double valorTotal = 0;

        for (Produto produto : listaProdutos) {
            valorTotal += produto.getValor() * produto.getCarrinho();
        }

        // Solicita confirmação do usuário
        ConfirmacaoPedido confirmacaoPedido = new ConfirmacaoPedido(this.pai, true, valorTotal);
        confirmacaoPedido.setVisible(true);
        if (confirmacaoPedido.isConfirmado()) {
            List<Item> listaItens = new ArrayList<Item>();

            for (Produto produto : this.listaProdutos) {
                if (produto.getCarrinho() > 0) {
                    Item item = new Item(produto);
                    listaItens.add(item);
                }
            }
            Pedido pedido = new Pedido(-1, "S", confirmacaoPedido.getDataHota(),
                    confirmacaoPedido.getComentario(), listaItens);
            OperacoesPedido operacoesPedido = new OperacoesPedido();
            int idPedido = operacoesPedido.insere(pedido);

            if (idPedido >= Util.RETORNO_SUCESSO) {
                JOptionPane.showMessageDialog(this.pai, "Pedido realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.listaProdutosCarrinho.clear();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this.pai, "Não foi possível realizar o pedido! Tente novamente mais tarde.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // Fluxo de telas
    private void chamaTelaEdicaoProdutoCarrinho(int id) {
        OperacoesProduto novoProduto = new OperacoesProduto();
        Produto produto = novoProduto.busca(id);
        produto.setCarrinho(this.listaProdutosCarrinho.get(id));

        if (produto instanceof ProdutoPronto) {
            AdicaoProdutoCarrinho adicaoProdutoCarrinho = new AdicaoProdutoCarrinho(this.pai, true, produto);
            adicaoProdutoCarrinho.setVisible(true);
            int quantidadeComprada = adicaoProdutoCarrinho.getQuantidade();
            this.listaProdutosCarrinho.put(id, quantidadeComprada);
            this.atualizaTabela();
        } else {
            CriacaoProdutoPersonalizado criacaoProdutoPersonalizado
                    = new CriacaoProdutoPersonalizado(this.pai, true, (ProdutoPersonalizado) produto);
            criacaoProdutoPersonalizado.setVisible(true);
            if (criacaoProdutoPersonalizado.isEditado()) {
                this.listaProdutosCarrinho.remove(id);
                int idProdutoNovo = criacaoProdutoPersonalizado.getIdProduto();
                int quantidadeComprada = criacaoProdutoPersonalizado.getQuantidade();
                this.listaProdutosCarrinho.put(idProdutoNovo, quantidadeComprada);
                this.atualizaTabela();
            }
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

        painelAcoes = new javax.swing.JPanel();
        botaoVoltar = new javax.swing.JButton();
        botaoLimparCarrinho = new javax.swing.JButton();
        botaoFecharPedido = new javax.swing.JButton();
        painelTabela = new javax.swing.JPanel();
        painelRolavelTabela = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Carrinho");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        painelAcoes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        botaoVoltar.setText("Voltar");
        botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVoltarActionPerformed(evt);
            }
        });
        painelAcoes.add(botaoVoltar);

        botaoLimparCarrinho.setText("Limpar carrinho");
        botaoLimparCarrinho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLimparCarrinhoActionPerformed(evt);
            }
        });
        painelAcoes.add(botaoLimparCarrinho);

        botaoFecharPedido.setText("Fechar pedido");
        botaoFecharPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharPedidoActionPerformed(evt);
            }
        });
        painelAcoes.add(botaoFecharPedido);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(painelAcoes, gridBagConstraints);

        painelTabela.setLayout(new java.awt.BorderLayout());

        tabelaProdutos.setModel(this.getModeloTabela());
        tabelaProdutos.removeColumn(tabelaProdutos.getColumnModel().getColumn(0));
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);
        painelRolavelTabela.setViewportView(tabelaProdutos);

        painelTabela.add(painelRolavelTabela, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        getContentPane().add(painelTabela, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoLimparCarrinhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLimparCarrinhoActionPerformed
        this.limparCarrinho();
    }//GEN-LAST:event_botaoLimparCarrinhoActionPerformed

    private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoVoltarActionPerformed

    private void botaoFecharPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharPedidoActionPerformed
        this.fecharPedido();
    }//GEN-LAST:event_botaoFecharPedidoActionPerformed

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
            java.util.logging.Logger.getLogger(Carrinho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Carrinho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Carrinho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Carrinho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Carrinho dialog = new Carrinho(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoFecharPedido;
    private javax.swing.JButton botaoLimparCarrinho;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JScrollPane painelRolavelTabela;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JTable tabelaProdutos;
    // End of variables declaration//GEN-END:variables
}
