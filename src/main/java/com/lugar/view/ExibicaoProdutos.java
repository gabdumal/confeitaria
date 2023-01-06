/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.lugar.view;

import com.lugar.confeitaria.Util;
import com.lugar.controller.Conexao;
import com.lugar.controller.OperacoesProdutoPronto;
import com.lugar.model.Usuario;
import com.lugar.model.ProdutoPronto;
import com.lugar.model.tables.ProdutosProntosTableModel;
import com.lugar.view.cliente.AdicaoProdutoCarrinho;
import com.lugar.view.cliente.Carrinho;
import com.lugar.view.cliente.CriacaoProdutoPersonalizado;
import com.lugar.view.funcionario.CadastroProduto;
import com.lugar.view.funcionario.EdicaoProduto;
import com.lugar.view.funcionario.ExibicaoPedidos;
import com.lugar.view.funcionario.ExibicaoTransacoes;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author lugar
 */
public class ExibicaoProdutos extends javax.swing.JFrame {

    // Geral
    private Usuario usuario;
    private List<ProdutoPronto> listaProdutos;
    private ProdutosProntosTableModel modeloTabela;
    private OperacoesProdutoPronto operacoesProdutoPronto;

    // Cliente
    private Map<Integer, Integer> listaProdutosCarrinho; // idProduto - Quantidade no carrinho

    public ExibicaoProdutos() {
        initComponents();
    }

    public ExibicaoProdutos(Usuario usuario) {
        this.usuario = usuario;
        this.operacoesProdutoPronto = new OperacoesProdutoPronto();

        if (!usuario.isAdmin()) {
            this.listaProdutosCarrinho = new HashMap<Integer, Integer>();
        }

        initComponents();

        if (usuario.isAdmin()) {
            // Funcionário
            tabelaProdutos.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    JTable tabela = (JTable) mouseEvent.getSource();
                    Point ponto = mouseEvent.getPoint();
                    int linha = tabela.rowAtPoint(ponto);
                    // Clique duplo
                    if (mouseEvent.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                        // Tela de edição
                        chamaTelaEdicao((int) modeloTabela.getValueAt(linha, 0));
                    }
                }
            });
        } else {
            // Cliente
            tabelaProdutos.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    JTable tabela = (JTable) mouseEvent.getSource();
                    Point ponto = mouseEvent.getPoint();
                    int linha = tabela.rowAtPoint(ponto);
                    // Clique duplo
                    if (mouseEvent.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                        chamaTelaAdicaoProdutoCarrinho((int) modeloTabela.getValueAt(linha, 0));
                    }
                }
            });
        }
    }

    private ProdutosProntosTableModel getModeloTabela() {
        this.atualizaModeloTabela();
        return this.modeloTabela;
    }

    private void atualizaModeloTabela() {
        this.listaProdutos = this.operacoesProdutoPronto.buscaTodos(usuario.isAdmin());
        // Reduz o estoque pela quantidade no carrinho para o cliente
        if (!usuario.isAdmin()) {
            for (ProdutoPronto produto : this.listaProdutos) {
                int id = produto.getId();
                int quantidadeCarrinho = this.listaProdutosCarrinho.getOrDefault(id, 0);
                int estoque = produto.getEstoque();
                produto.setEstoque(estoque - quantidadeCarrinho);
            }
        }
        this.modeloTabela = new ProdutosProntosTableModel(this.listaProdutos);
    }

    private void atualizaTabela() {
        this.atualizaModeloTabela();
        tabelaProdutos.setModel(this.modeloTabela);
        tabelaProdutos.removeColumn(tabelaProdutos.getColumnModel().getColumn(0));
    }

    // Fluxo de telas
    private void chamaTelaEdicao(int id) {
        EdicaoProduto edicaoProduto = new EdicaoProduto(this, true, id);
        edicaoProduto.setVisible(true);
        this.atualizaTabela();
    }

    private void chamaTelaAdicaoProdutoCarrinho(int id) {
        OperacoesProdutoPronto novoProdutoPronto = new OperacoesProdutoPronto();
        ProdutoPronto produto = novoProdutoPronto.busca(id);
        produto.setCarrinho(this.listaProdutosCarrinho.getOrDefault(id, 1));
        AdicaoProdutoCarrinho adicaoProdutoCarrinho = new AdicaoProdutoCarrinho(this, true, produto);
        adicaoProdutoCarrinho.setVisible(true);
        int quantidadeComprada = adicaoProdutoCarrinho.getQuantidade();
        this.listaProdutosCarrinho.put(id, quantidadeComprada);
        this.atualizaTabela();
    }

    private void chamaTelaCadastroProduto() {
        CadastroProduto cadastroProduto = new CadastroProduto(this, true);
        cadastroProduto.setVisible(true);
        this.atualizaTabela();
    }

    private void chamaTelaCarrinho() {
        Carrinho carrinho = new Carrinho(this, true, usuario.getId(), listaProdutosCarrinho);
        carrinho.setVisible(true);
        this.listaProdutosCarrinho = carrinho.getListaProdutosCarrinho();
        this.atualizaTabela();
    }

    private void chamaTelaCriacaoProdutoPersonalizado() {
        CriacaoProdutoPersonalizado criacaoProdutoPersonalizado = new CriacaoProdutoPersonalizado(this, true);
        criacaoProdutoPersonalizado.setVisible(true);
        int idProduto = criacaoProdutoPersonalizado.getIdProduto();
        if (idProduto >= Util.RETORNO_SUCESSO) {
            int quantidade = criacaoProdutoPersonalizado.getQuantidade();
            this.listaProdutosCarrinho.put(idProduto, quantidade);
        }
    }

    private void chamaTelaTransacoes() {
        ExibicaoTransacoes tela = new ExibicaoTransacoes(this, true);
        tela.setVisible(true);
    }

    private void chamaTelaPedidos() {
        ExibicaoPedidos tela = new ExibicaoPedidos(this, true);
        tela.setVisible(true);
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

        painelTabela = new javax.swing.JPanel();
        painelRolavelTabela = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        barraMenu = new javax.swing.JMenuBar();
        menuProdutos = new javax.swing.JMenu();
        itemMenuAdicionarProduto = new javax.swing.JMenuItem();
        itemMenuCriarProdutoPersonalizado = new javax.swing.JMenuItem();
        menuPedidos = new javax.swing.JMenu();
        itemMenuCarrinho = new javax.swing.JMenuItem();
        itemMenuListaPedidos = new javax.swing.JMenuItem();
        menuTransacoes = new javax.swing.JMenu();
        itemMenuLista = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vitrine");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        painelTabela.setLayout(new java.awt.BorderLayout());

        tabelaProdutos.setModel(this.getModeloTabela());
        tabelaProdutos.removeColumn(tabelaProdutos.getColumnModel().getColumn(0));
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);
        painelRolavelTabela.setViewportView(tabelaProdutos);

        painelTabela.add(painelRolavelTabela, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(painelTabela, gridBagConstraints);

        menuProdutos.setText("Produtos");
        menuProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProdutosActionPerformed(evt);
            }
        });

        if(usuario.isAdmin()){
            itemMenuAdicionarProduto.setText("Adicionar produto");
            itemMenuAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemMenuAdicionarProdutoActionPerformed(evt);
                }
            });
            menuProdutos.add(itemMenuAdicionarProduto);
        }

        if(!usuario.isAdmin()){
            itemMenuCriarProdutoPersonalizado.setText("Encomendar personalizado");
            itemMenuCriarProdutoPersonalizado.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemMenuCriarProdutoPersonalizadoActionPerformed(evt);
                }
            });
            menuProdutos.add(itemMenuCriarProdutoPersonalizado);
        }

        barraMenu.add(menuProdutos);

        menuPedidos.setText("Pedidos");

        if(!usuario.isAdmin()){
            itemMenuCarrinho.setText("Carrinho");
            itemMenuCarrinho.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemMenuCarrinhoActionPerformed(evt);
                }
            });
            menuPedidos.add(itemMenuCarrinho);
        }

        if(usuario.isAdmin()){
            itemMenuListaPedidos.setText("Lista");
            itemMenuListaPedidos.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemMenuListaPedidosActionPerformed(evt);
                }
            });
            menuPedidos.add(itemMenuListaPedidos);
        }

        barraMenu.add(menuPedidos);

        menuTransacoes.setText("Transações");
        menuTransacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTransacoesActionPerformed(evt);
            }
        });

        if(usuario.isAdmin()){
            itemMenuLista.setText("Lista");
            itemMenuLista.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemMenuListaActionPerformed(evt);
                }
            });
            menuTransacoes.add(itemMenuLista);
        }

        if(usuario.isAdmin()){

            barraMenu.add(menuTransacoes);
        }

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemMenuAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuAdicionarProdutoActionPerformed
        this.chamaTelaCadastroProduto();
    }//GEN-LAST:event_itemMenuAdicionarProdutoActionPerformed

    private void itemMenuCarrinhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCarrinhoActionPerformed
        this.chamaTelaCarrinho();
    }//GEN-LAST:event_itemMenuCarrinhoActionPerformed

    private void itemMenuListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuListaActionPerformed
        this.chamaTelaTransacoes();
    }//GEN-LAST:event_itemMenuListaActionPerformed

    private void itemMenuCriarProdutoPersonalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCriarProdutoPersonalizadoActionPerformed
        this.chamaTelaCriacaoProdutoPersonalizado();
    }//GEN-LAST:event_itemMenuCriarProdutoPersonalizadoActionPerformed

    private void menuProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProdutosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuProdutosActionPerformed

    private void menuTransacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTransacoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuTransacoesActionPerformed

    private void itemMenuListaPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuListaPedidosActionPerformed
        this.chamaTelaPedidos();
    }//GEN-LAST:event_itemMenuListaPedidosActionPerformed

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
            java.util.logging.Logger.getLogger(ExibicaoProdutos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExibicaoProdutos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExibicaoProdutos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExibicaoProdutos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExibicaoProdutos().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JMenuItem itemMenuAdicionarProduto;
    private javax.swing.JMenuItem itemMenuCarrinho;
    private javax.swing.JMenuItem itemMenuCriarProdutoPersonalizado;
    private javax.swing.JMenuItem itemMenuLista;
    private javax.swing.JMenuItem itemMenuListaPedidos;
    private javax.swing.JMenu menuPedidos;
    private javax.swing.JMenu menuProdutos;
    private javax.swing.JMenu menuTransacoes;
    private javax.swing.JScrollPane painelRolavelTabela;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JTable tabelaProdutos;
    // End of variables declaration//GEN-END:variables
}
