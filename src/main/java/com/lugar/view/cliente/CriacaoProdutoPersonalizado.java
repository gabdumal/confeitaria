/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.lugar.view.cliente;

import com.lugar.confeitaria.Util;
import com.lugar.controller.Conexao;
import com.lugar.controller.OperacoesCaracteristica;
import com.lugar.model.Caracteristica;
import com.lugar.model.Forma;
import com.lugar.model.ProdutoPersonalizado;
import com.lugar.model.SetStringString;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author lugar
 */
public class CriacaoProdutoPersonalizado extends javax.swing.JDialog {

    private int idProduto;
    private int quantidade;
    private String receita;
    private int recheios;
    private boolean editado;
    private List<Caracteristica> listaFormas;
    private List<Caracteristica> listaCores;
    private List<Caracteristica> listaCoberturas;
    private List<Caracteristica> listaRecheios;
    private DefaultComboBoxModel modeloReceita;
    private DefaultComboBoxModel modeloFormas;
    private DefaultComboBoxModel modeloCores;
    private DefaultComboBoxModel modeloCoberturas;
    private DefaultComboBoxModel modeloRecheios1;
    private DefaultComboBoxModel modeloRecheios2;
    private DefaultComboBoxModel modeloRecheios3;
    private java.awt.Frame pai;

    public CriacaoProdutoPersonalizado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.idProduto = -1;
        this.receita = "B";
        this.pai = parent;
        this.editado = false;
        this.carregaComboBoxes();
        initComponents();
        this.trocaPainel();
        this.trocaExibicaoRecheios();
    }

    public CriacaoProdutoPersonalizado(java.awt.Frame parent, boolean modal, ProdutoPersonalizado produto) {
        super(parent, modal);
        this.idProduto = produto.getId();
        this.receita = "B";
        this.pai = parent;
        this.editado = false;
        this.carregaComboBoxes();
        initComponents();
        this.campoQuantidade.setValue(produto.getCarrinho());
        this.areaTextoDetalheBolo.setText(produto.getDetalhe());
        this.areaTextoDetalheTrufa.setText(produto.getDetalhe());
        for (Caracteristica forma : listaFormas) {
            if (forma.getId() == produto.getForma().getId()) {
                this.comboBoxFormaBolo.setSelectedItem(forma);
                break;
            }
        }
        for (Caracteristica cobertura : listaCoberturas) {
            if (cobertura.getId() == produto.getCobertura().getId()) {
                this.comboBoxFormaBolo.setSelectedItem(cobertura);
                break;
            }
        }
        for (Caracteristica cor : listaCores) {
            if (cor.getId() == produto.getCor().getId()) {
                this.comboBoxFormaBolo.setSelectedItem(cor);
                break;
            }
        }
        boolean preencheu1 = false, preencheu2 = false;
        for (Caracteristica recheio : listaRecheios) {
            if (!preencheu1 && recheio.getId() == produto.getRecheio(0).getId()) {
                this.comboBoxRecheioBolo1.setSelectedItem(recheio);
                preencheu1 = true;
                continue;
            } else if (!preencheu2 && this.recheios >= 2 && recheio.getId() == produto.getRecheio(1).getId()) {
                this.comboBoxRecheioBolo1.setSelectedItem(recheio);
                preencheu2 = true;
                continue;
            } else if (this.recheios >= 3 && recheio.getId() == produto.getRecheio(2).getId()) {
                this.comboBoxRecheioBolo1.setSelectedItem(recheio);
                break;
            }
        }

        this.comboBoxCoberturaBolo.setSelectedItem(produto.getCobertura());
        this.trocaExibicaoRecheios();
        this.trocaPainel();
    }

    public int getIdProduto() {
        return this.idProduto;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public boolean isEditado() {
        return this.editado;
    }

    private void carregaComboBoxes() {
        this.modeloReceita = new DefaultComboBoxModel();
        this.modeloFormas = new DefaultComboBoxModel();
        this.modeloCores = new DefaultComboBoxModel();
        this.modeloCoberturas = new DefaultComboBoxModel();
        this.modeloRecheios1 = new DefaultComboBoxModel();
        this.modeloRecheios2 = new DefaultComboBoxModel();
        this.modeloRecheios3 = new DefaultComboBoxModel();
        SetStringString receitaBolo = new SetStringString("B", "Bolo");
        SetStringString receitaTrufa = new SetStringString("T", "Trufa");
        this.modeloReceita.addElement(receitaBolo);
        this.modeloReceita.addElement(receitaTrufa);
        OperacoesCaracteristica novaListaDeListas = new OperacoesCaracteristica();
        List<List<Caracteristica>> listaDeListasDeCaracteristicas = novaListaDeListas.buscaTodos();
        this.listaFormas = listaDeListasDeCaracteristicas.get(0);
        this.listaCores = listaDeListasDeCaracteristicas.get(1);
        this.listaCoberturas = listaDeListasDeCaracteristicas.get(2);
        this.listaRecheios = listaDeListasDeCaracteristicas.get(3);
        for (Caracteristica caracteristica : this.listaFormas) {
            this.modeloFormas.addElement(caracteristica);
        }
        for (Caracteristica caracteristica : this.listaCores) {
            this.modeloCores.addElement(caracteristica);
        }
        for (Caracteristica caracteristica : this.listaCoberturas) {
            this.modeloCoberturas.addElement(caracteristica);
        }
        for (Caracteristica caracteristica : this.listaRecheios) {
            this.modeloRecheios1.addElement(caracteristica);
            this.modeloRecheios2.addElement(caracteristica);
            this.modeloRecheios3.addElement(caracteristica);
        }
    }

    private void trocaPainel() {
        if (this.receita.equals("B")) {
            this.painelCamposBolo.setVisible(true);
            this.painelCamposTrufa.setVisible(false);
        } else {
            this.painelCamposBolo.setVisible(false);
            this.painelCamposTrufa.setVisible(true);
        }
    }

    private void trocaExibicaoRecheios() {
        this.recheios = ((Forma) this.comboBoxFormaBolo.getSelectedItem()).getRecheios();
        this.comboBoxRecheioBolo2.setVisible(false);
        this.comboBoxRecheioBolo3.setVisible(false);
        if (this.recheios >= 2) {
            this.comboBoxRecheioBolo2.setVisible(true);
        }
        if (this.recheios >= 3) {
            this.comboBoxRecheioBolo3.setVisible(true);
        }
    }

    private void criaProdutoPersonalizado() {
        Caracteristica corForm = (Caracteristica) this.comboBoxCorBolo.getSelectedItem();
        Caracteristica recheioForm1 = (Caracteristica) this.comboBoxRecheioBolo1.getSelectedItem();

        if (this.receita.equals(Util.RECEITA_BOLO)) {
            List<Caracteristica> listaRecheiosForm = new ArrayList<Caracteristica>();
            listaRecheiosForm.add(recheioForm1);
            Caracteristica formaForm = (Caracteristica) this.comboBoxFormaBolo.getSelectedItem();
            Caracteristica coberturaForm = (Caracteristica) this.comboBoxCoberturaBolo.getSelectedItem();
            if (this.recheios >= 2) {
                listaRecheiosForm.add((Caracteristica) this.comboBoxRecheioBolo2.getSelectedItem());
            }
            if (this.recheios >= 3) {
                listaRecheiosForm.add((Caracteristica) this.comboBoxRecheioBolo3.getSelectedItem());
            }
            String detalheForm = this.areaTextoDetalheBolo.getText().trim();

            ProdutoPersonalizado produtoPersonalizado
                    = new ProdutoPersonalizado(this.idProduto, this.receita,
                            detalheForm, formaForm, corForm,
                            coberturaForm, listaRecheiosForm);

            this.idProduto = Conexao.insereProdutoPersonalizado(produtoPersonalizado);
            this.quantidade = (int) this.campoQuantidade.getValue();
        }

        if (this.idProduto >= Util.RETORNO_SUCESSO) {
            JOptionPane.showMessageDialog(this.pai, "Produto adicionado ao carrinho!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.editado = true;
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this.pai, "Não foi possível adicionar o produto ao carrinho! Tente novamente mais tarde.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        painelInformacoesProduto = new javax.swing.JPanel();
        textoPrecoProduto = new javax.swing.JLabel();
        painelTipoQuantidade = new javax.swing.JPanel();
        textoReceita = new javax.swing.JLabel();
        comboBoxReceita = new javax.swing.JComboBox<>();
        textoQuantidade = new javax.swing.JLabel();
        campoQuantidade = new javax.swing.JSpinner();
        painelCamposTrufa = new javax.swing.JPanel();
        textoRecheioTrufa = new javax.swing.JLabel();
        comboBoxRecheioTrufa = new javax.swing.JComboBox<>();
        textoCoberturaTrufa = new javax.swing.JLabel();
        comboBoxCoberturaTrufa = new javax.swing.JComboBox<>();
        textoDetalheTrufa = new javax.swing.JLabel();
        painelAreaTextoDetalheTrufa = new javax.swing.JScrollPane();
        areaTextoDetalheTrufa = new javax.swing.JTextPane();
        painelCamposBolo = new javax.swing.JPanel();
        textoFormaBolo = new javax.swing.JLabel();
        comboBoxFormaBolo = new javax.swing.JComboBox<>();
        painelCamposRecheiosBolo = new javax.swing.JPanel();
        comboBoxRecheioBolo1 = new javax.swing.JComboBox<>();
        comboBoxRecheioBolo2 = new javax.swing.JComboBox<>();
        comboBoxRecheioBolo3 = new javax.swing.JComboBox<>();
        textoCoberturaBolo = new javax.swing.JLabel();
        comboBoxCoberturaBolo = new javax.swing.JComboBox<>();
        textoCorBolo = new javax.swing.JLabel();
        comboBoxCorBolo = new javax.swing.JComboBox<>();
        textoDetalheBolo = new javax.swing.JLabel();
        painelAreaTextoDetalheBolo = new javax.swing.JScrollPane();
        areaTextoDetalheBolo = new javax.swing.JTextPane();
        painelBotoes = new javax.swing.JPanel();
        botaoCancelar = new javax.swing.JButton();
        botaoEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar produto ao Carrinho");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        painelFormulario.setLayout(new java.awt.GridBagLayout());

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Produto personalizado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        painelFormulario.add(titulo, gridBagConstraints);

        painelInformacoesProduto.setLayout(new java.awt.GridBagLayout());

        textoPrecoProduto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textoPrecoProduto.setText("R$ 0,00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelInformacoesProduto.add(textoPrecoProduto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        painelFormulario.add(painelInformacoesProduto, gridBagConstraints);

        painelTipoQuantidade.setLayout(new java.awt.GridBagLayout());

        textoReceita.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textoReceita.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelTipoQuantidade.add(textoReceita, gridBagConstraints);

        comboBoxReceita.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        comboBoxReceita.setModel(this.modeloReceita);
        comboBoxReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxReceitaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelTipoQuantidade.add(comboBoxReceita, gridBagConstraints);

        textoQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textoQuantidade.setText("Quantidade:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        painelTipoQuantidade.add(textoQuantidade, gridBagConstraints);

        campoQuantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99999, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        painelTipoQuantidade.add(campoQuantidade, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        painelFormulario.add(painelTipoQuantidade, gridBagConstraints);

        painelCamposTrufa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelCamposTrufa.setLayout(new java.awt.GridBagLayout());

        textoRecheioTrufa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoRecheioTrufa.setText("Recheio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        painelCamposTrufa.add(textoRecheioTrufa, gridBagConstraints);

        comboBoxRecheioTrufa.setModel(this.modeloRecheios1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        painelCamposTrufa.add(comboBoxRecheioTrufa, gridBagConstraints);

        textoCoberturaTrufa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoCoberturaTrufa.setText("Cobertura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCamposTrufa.add(textoCoberturaTrufa, gridBagConstraints);

        comboBoxCoberturaTrufa.setModel(this.modeloCoberturas);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        painelCamposTrufa.add(comboBoxCoberturaTrufa, gridBagConstraints);

        textoDetalheTrufa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoDetalheTrufa.setText("Detalhe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCamposTrufa.add(textoDetalheTrufa, gridBagConstraints);

        areaTextoDetalheTrufa.setText("\n\n");
        painelAreaTextoDetalheTrufa.setViewportView(areaTextoDetalheTrufa);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        painelCamposTrufa.add(painelAreaTextoDetalheTrufa, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        painelFormulario.add(painelCamposTrufa, gridBagConstraints);

        painelCamposBolo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelCamposBolo.setLayout(new java.awt.GridBagLayout());

        textoFormaBolo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoFormaBolo.setText("Forma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        painelCamposBolo.add(textoFormaBolo, gridBagConstraints);

        comboBoxFormaBolo.setModel(this.modeloFormas);
        comboBoxFormaBolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxFormaBoloActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        painelCamposBolo.add(comboBoxFormaBolo, gridBagConstraints);

        painelCamposRecheiosBolo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Recheios"));
        painelCamposRecheiosBolo.setLayout(new java.awt.GridBagLayout());

        comboBoxRecheioBolo1.setModel(this.modeloRecheios1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposRecheiosBolo.add(comboBoxRecheioBolo1, gridBagConstraints);

        comboBoxRecheioBolo2.setModel(this.modeloRecheios2);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposRecheiosBolo.add(comboBoxRecheioBolo2, gridBagConstraints);

        comboBoxRecheioBolo3.setModel(this.modeloRecheios3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposRecheiosBolo.add(comboBoxRecheioBolo3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        painelCamposBolo.add(painelCamposRecheiosBolo, gridBagConstraints);

        textoCoberturaBolo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoCoberturaBolo.setText("Cobertura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCamposBolo.add(textoCoberturaBolo, gridBagConstraints);

        comboBoxCoberturaBolo.setModel(this.modeloCoberturas);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        painelCamposBolo.add(comboBoxCoberturaBolo, gridBagConstraints);

        textoCorBolo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoCorBolo.setText("Cor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCamposBolo.add(textoCorBolo, gridBagConstraints);

        comboBoxCorBolo.setModel(this.modeloCores);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        painelCamposBolo.add(comboBoxCorBolo, gridBagConstraints);

        textoDetalheBolo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoDetalheBolo.setText("Detalhe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        painelCamposBolo.add(textoDetalheBolo, gridBagConstraints);

        areaTextoDetalheBolo.setText("\n\n");
        painelAreaTextoDetalheBolo.setViewportView(areaTextoDetalheBolo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        painelCamposBolo.add(painelAreaTextoDetalheBolo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        painelFormulario.add(painelCamposBolo, gridBagConstraints);

        painelBotoes.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        painelBotoes.add(botaoCancelar);

        botaoEnviar.setText("Enviar");
        botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });
        painelBotoes.add(botaoEnviar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        painelFormulario.add(painelBotoes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(painelFormulario, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed
        this.criaProdutoPersonalizado();
    }//GEN-LAST:event_botaoEnviarActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoCancelarActionPerformed

    private void comboBoxReceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxReceitaActionPerformed
        this.receita = ((SetStringString) comboBoxReceita.getSelectedItem()).getChave();
        this.trocaPainel();
    }//GEN-LAST:event_comboBoxReceitaActionPerformed

    private void comboBoxFormaBoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxFormaBoloActionPerformed
        this.trocaExibicaoRecheios();
    }//GEN-LAST:event_comboBoxFormaBoloActionPerformed

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
            java.util.logging.Logger.getLogger(CriacaoProdutoPersonalizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CriacaoProdutoPersonalizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CriacaoProdutoPersonalizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CriacaoProdutoPersonalizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CriacaoProdutoPersonalizado dialog = new CriacaoProdutoPersonalizado(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextPane areaTextoDetalheBolo;
    private javax.swing.JTextPane areaTextoDetalheTrufa;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoEnviar;
    private javax.swing.JSpinner campoQuantidade;
    private javax.swing.JComboBox<String> comboBoxCoberturaBolo;
    private javax.swing.JComboBox<String> comboBoxCoberturaTrufa;
    private javax.swing.JComboBox<String> comboBoxCorBolo;
    private javax.swing.JComboBox<String> comboBoxFormaBolo;
    private javax.swing.JComboBox<String> comboBoxReceita;
    private javax.swing.JComboBox<String> comboBoxRecheioBolo1;
    private javax.swing.JComboBox<String> comboBoxRecheioBolo2;
    private javax.swing.JComboBox<String> comboBoxRecheioBolo3;
    private javax.swing.JComboBox<String> comboBoxRecheioTrufa;
    private javax.swing.JScrollPane painelAreaTextoDetalheBolo;
    private javax.swing.JScrollPane painelAreaTextoDetalheTrufa;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelCamposBolo;
    private javax.swing.JPanel painelCamposRecheiosBolo;
    private javax.swing.JPanel painelCamposTrufa;
    private javax.swing.JPanel painelFormulario;
    private javax.swing.JPanel painelInformacoesProduto;
    private javax.swing.JPanel painelTipoQuantidade;
    private javax.swing.JLabel textoCoberturaBolo;
    private javax.swing.JLabel textoCoberturaTrufa;
    private javax.swing.JLabel textoCorBolo;
    private javax.swing.JLabel textoDetalheBolo;
    private javax.swing.JLabel textoDetalheTrufa;
    private javax.swing.JLabel textoFormaBolo;
    private javax.swing.JLabel textoPrecoProduto;
    private javax.swing.JLabel textoQuantidade;
    private javax.swing.JLabel textoReceita;
    private javax.swing.JLabel textoRecheioTrufa;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
