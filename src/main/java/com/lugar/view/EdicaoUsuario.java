/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.lugar.view;

import com.lugar.confeitaria.Util;
import com.lugar.controller.OperacoesCliente;
import com.lugar.controller.OperacoesFuncionario;
import com.lugar.controller.OperacoesUsuario;
import com.lugar.model.Cliente;
import com.lugar.model.Endereco;
import com.lugar.model.Funcionario;
import com.lugar.model.PessoaFisica;
import com.lugar.model.PessoaJuridica;
import com.lugar.model.Usuario;
import com.lugar.model.exceptions.ExcecaoAtributo;
import com.lugar.model.exceptions.ExcecaoDataInvalida;
import com.lugar.model.exceptions.ExcecaoEnderecoInvalido;
import com.lugar.model.exceptions.ExcecaoUsuarioInvalido;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lugar
 *Anna Júlia de Almeida Lucas - 2021760029
 *Celso Gabriel Dutra Almeida Malosto - 202176002
 *Lucas Paiva dos Santos - 2021760026
 *Rodrigo Soares de Assis - 202176027
 */
public class EdicaoUsuario extends javax.swing.JDialog {

    private int id;
    private java.awt.Frame pai;
    private Usuario usuario;
    private OperacoesUsuario operacoesUsuario;

    public EdicaoUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public EdicaoUsuario(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        this.id = id;
        this.pai = parent;
        this.operacoesUsuario = new OperacoesUsuario();
        this.usuario = operacoesUsuario.busca(id);

        initComponents();

        // Preenchendo campos
        campoNome.setText(usuario.getNome());
        campoNomeUsuario.setText(usuario.getNomeUsuario());
        campoEmail.setText(usuario.getEmail());
        campoTelefone.setText(usuario.getTelefoneFormatado());
        Endereco endereco = usuario.getEndereco();
        campoLogradouro.setText(endereco.getLogradouro());
        campoNumero.setText(endereco.getNumero());
        campoComplemento.setText(endereco.getComplemento());
        campoBairro.setText(endereco.getBairro());
        campoCidade.setText(endereco.getCidade());
        campoCep.setText(endereco.getCepFormatado());
        campoIdentificador.setText(usuario.getIdentificadorFormatado());
        campoUf.setSelectedItem(endereco.getUf());
        campoSenha.setText(usuario.getSenhaHash());

        if (usuario instanceof Funcionario) {
            textoIdentificador.setText("CPF:");
            campoMatricula.setText(((Funcionario) usuario).getMatricula());
            campoFuncaoFuncionario.setText(((Funcionario) usuario).getFuncao());
            textoCartaoCredito.setVisible(false);
            campoCartao.setVisible(false);
            textoDataDeNascimento.setVisible(false);
            campoDataDeNascimento.setVisible(false);
            textoRazaoSocial.setVisible(false);
            campoRazaoSocial.setVisible(false);
        } else {
            campoCartao.setText(((Cliente) usuario).getCartao());
            if (usuario instanceof PessoaFisica) {
                textoIdentificador.setText("CPF:");
                campoDataDeNascimento.setText(((PessoaFisica) usuario).getDataNascimentoFormatada());
                textoRazaoSocial.setVisible(false);
                campoRazaoSocial.setVisible(false);
                textoFuncaoFuncionario.setVisible(false);
                campoFuncaoFuncionario.setVisible(false);
                textoMatricula.setVisible(false);
                campoMatricula.setVisible(false);
            } else if (usuario instanceof PessoaJuridica) {
                textoIdentificador.setText("CNPJ:");
                campoRazaoSocial.setText(((PessoaJuridica) usuario).getRazaoSocial());
                textoDataDeNascimento.setVisible(false);
                campoDataDeNascimento.setVisible(false);
                textoFuncaoFuncionario.setVisible(false);
                campoFuncaoFuncionario.setVisible(false);
                textoMatricula.setVisible(false);
                campoMatricula.setVisible(false);
            }
        }
    }

    private void editaUsuario() {
        String nomeForm = campoNome.getText().trim();
        String nomeUsuarioForm = campoNomeUsuario.getText().trim();
        String senhaForm = String.valueOf(campoSenha.getPassword()).trim();
        String emailForm = campoEmail.getText().trim();
        String telefoneForm = campoTelefone.getText().replaceAll(" ", "").replaceAll("[(]", "").replaceAll("[)]", "").replaceAll("[-]", "");
        String logradouroForm = campoLogradouro.getText().trim();
        String numeroForm = campoNumero.getText().trim();
        String complementoForm = campoComplemento.getText().trim();
        String bairroForm = campoBairro.getText().trim();
        String cidadeForm = campoCidade.getText().trim();
        String ufForm = (String) campoUf.getSelectedItem();
        String cepForm = campoCep.getText().replaceAll(" ", "").replaceAll("[-]", "");
        String cartaoForm = campoCartao.getText().trim().replaceAll(" ", "").replaceAll("[.]", "");
        String identificadorForm = campoIdentificador.getText().replaceAll(" ", "").replaceAll("[-]", "").replaceAll("[.]", "").replaceAll("[/]", "");

        boolean confirmacao = JOptionPane.showConfirmDialog(null, "Deseja confirmar a edição do perfil?", "Edição de Perfil", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
        if (confirmacao) {
            int valorRetorno = Util.RETORNO_ERRO_INDETERMINADO;
            try {
                Endereco enderecoForm = new Endereco(usuario.getEndereco().getId(), numeroForm, complementoForm, logradouroForm, bairroForm, cidadeForm, ufForm, cepForm);
                if (usuario instanceof Funcionario) {
                    OperacoesFuncionario operacoesFuncionario = new OperacoesFuncionario();
                    Funcionario funcionarioForm = new Funcionario(usuario.getId(), nomeForm, nomeUsuarioForm, senhaForm, emailForm, telefoneForm, identificadorForm, enderecoForm);
                    valorRetorno = operacoesFuncionario.atualiza(funcionarioForm);
                } else {
                    Cliente cliente;
                    OperacoesCliente operacoesCliente = new OperacoesCliente();
                    if (usuario instanceof PessoaFisica) {
                        String dataNascimentoForm = campoDataDeNascimento.getText().trim();
                        LocalDate dataNascimento = Util.converteData(dataNascimentoForm);
                        cliente = new PessoaFisica(usuario.getId(), nomeForm,
                                nomeUsuarioForm, senhaForm,
                                identificadorForm, emailForm,
                                telefoneForm, enderecoForm, cartaoForm,
                                dataNascimento);
                    } else {
                        String razaoSocialForm = campoRazaoSocial.getText().trim();
                        cliente = new PessoaJuridica(usuario.getId(), nomeForm,
                                nomeUsuarioForm, senhaForm,
                                identificadorForm, emailForm,
                                telefoneForm, enderecoForm, cartaoForm,
                                razaoSocialForm
                        );
                    }
                    valorRetorno = operacoesCliente.atualiza(cliente);
                }
                if (valorRetorno == Util.RETORNO_SUCESSO) {
                    JOptionPane.showMessageDialog(this.pai, "Edição realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                } else if (valorRetorno == Util.RETORNO_ERRO_NAO_UNICO) {
                    JOptionPane.showMessageDialog(null, "Infelizmente não foi possivel editar os dados, pois um usuário com este nome de usuário ou e-mail já existe no sistema.");
                } else {
                    JOptionPane.showMessageDialog(this.pai, "Infelizmente não foi possivel editar os dados, tente novamente mais tarde!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ExcecaoDataInvalida ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível atualizar os dados do perfil! A data de nascimento foi preenchida de forma inválida.");
            } catch (ExcecaoEnderecoInvalido ex) {
                String mensagemErro = "Não foi possível atualizar os dados do perfil! O endereço foi preenchido de forma inválida.";
                Throwable cause = ex.getCause();
                if (cause instanceof ExcecaoAtributo) {
                    mensagemErro += "\n" + ((ExcecaoAtributo) cause).getMessage();
                }
                JOptionPane.showMessageDialog(null, mensagemErro);
                Logger.getLogger(CadastroCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExcecaoUsuarioInvalido ex) {
                String mensagemErro = "Não foi possível atualizar os dados do perfil!";
                Throwable cause = ex.getCause();
                if (cause instanceof ExcecaoAtributo) {
                    mensagemErro += "\n" + ((ExcecaoAtributo) cause).getMessage();
                }
                JOptionPane.showMessageDialog(null, mensagemErro);
                Logger.getLogger(CadastroCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//
//    private boolean validaDados(String nomeForm,
//            String nomeUsuarioForm,
//            String senhaForm,
//            String emailForm,
//            String telefoneForm,
//            String cartaoForm,
//            String identificadorForm,
//            Endereco endereco
//    ) {
//        String logradouro = endereco.getLogradouro();
//        String numero = endereco.getNumero();
//        String complemento = endereco.getComplemento();
//        String bairro = endereco.getBairro();
//        String cidade = endereco.getCidade();
//        String cep = endereco.getCep();
//
//        if (usuario.isAdmin()) {
//            cartaoForm = "0000000000000000";
//        }
//
//        if (nomeForm.isBlank() || nomeUsuarioForm.isBlank()
//                || senhaForm.isBlank() || emailForm.isBlank()
//                || telefoneForm.isBlank() || cartaoForm.isBlank()
//                || identificadorForm.isBlank() || logradouro.isBlank()
//                || numero.isBlank() || bairro.isBlank()
//                || cidade.isBlank() || cep.isBlank()) {
//            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
//            return false;
//        } else if (nomeUsuarioForm.contains(" ")
//                || senhaForm.contains(" ")
//                || emailForm.contains(" ")
//                || cartaoForm.contains(" ")) {
//            JOptionPane.showMessageDialog(null, "Os campos nome de usuário, senha, email e cartão não devem conter espaço");
//            return false;
//        } else if (cartaoForm.length() != 16) {
//            JOptionPane.showMessageDialog(null, "Numero de cartao invalido");
//            return false;
//        }
//        /*
//        TODO tratar CNPJ e CPF
//        else if (identificadorForm.length() != 11) {
//            JOptionPane.showMessageDialog(null, "Numero de cartao invalido");
//            return 3;}*/
////        else {
////            return 0;
//////        }
////        }
//        return true;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        botoesTipoPessoa = new javax.swing.ButtonGroup();
        painelFormulario = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        painelCampos = new javax.swing.JPanel();
        textoNome = new javax.swing.JLabel();
        campoNome = new javax.swing.JFormattedTextField();
        textoSenha = new javax.swing.JLabel();
        campoSenha = new javax.swing.JPasswordField();
        textoNomeUsuario = new javax.swing.JLabel();
        campoNomeUsuario = new javax.swing.JFormattedTextField();
        textoEmail = new javax.swing.JLabel();
        campoEmail = new javax.swing.JFormattedTextField();
        textoTelefone = new javax.swing.JLabel();
        campoTelefone = new javax.swing.JFormattedTextField();
        textoIdentificador = new javax.swing.JLabel();
        campoIdentificador = new javax.swing.JFormattedTextField();
        textoCartaoCredito = new javax.swing.JLabel();
        campoCartao = new javax.swing.JFormattedTextField();
        textoRazaoSocial = new javax.swing.JLabel();
        campoRazaoSocial = new javax.swing.JFormattedTextField();
        textoMatricula = new javax.swing.JLabel();
        campoMatricula = new javax.swing.JFormattedTextField();
        textoFuncaoFuncionario = new javax.swing.JLabel();
        textoDataDeNascimento = new javax.swing.JLabel();
        campoDataDeNascimento = new javax.swing.JFormattedTextField();
        campoFuncaoFuncionario = new javax.swing.JFormattedTextField();
        painelCamposEndereco = new javax.swing.JPanel();
        textoLogradouro = new javax.swing.JLabel();
        campoLogradouro = new javax.swing.JFormattedTextField();
        textoNumero = new javax.swing.JLabel();
        campoNumero = new javax.swing.JFormattedTextField();
        textoComplemento = new javax.swing.JLabel();
        campoComplemento = new javax.swing.JFormattedTextField();
        textoBairro = new javax.swing.JLabel();
        campoBairro = new javax.swing.JFormattedTextField();
        textoCidade = new javax.swing.JLabel();
        campoCidade = new javax.swing.JFormattedTextField();
        textoUf = new javax.swing.JLabel();
        campoUf = new javax.swing.JComboBox<>();
        textoCep = new javax.swing.JLabel();
        campoCep = new javax.swing.JFormattedTextField();
        painelBotoes = new javax.swing.JPanel();
        botaoVoltar = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edição de Perfil");
        setMinimumSize(new java.awt.Dimension(500, 600));
        setPreferredSize(new java.awt.Dimension(375, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        painelFormulario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        painelFormulario.setMinimumSize(new java.awt.Dimension(550, 500));
        painelFormulario.setLayout(new java.awt.GridBagLayout());

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Perfil");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        painelFormulario.add(titulo, gridBagConstraints);

        painelCampos.setMinimumSize(new java.awt.Dimension(500, 600));
        painelCampos.setOpaque(false);
        painelCampos.setRequestFocusEnabled(false);
        painelCampos.setLayout(new java.awt.GridBagLayout());

        textoNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoNome.setText("Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoNome, gridBagConstraints);

        campoNome.setPreferredSize(new java.awt.Dimension(200, 22));
        campoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoNome, gridBagConstraints);

        textoSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoSenha.setText("Senha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoSenha, gridBagConstraints);

        campoSenha.setMinimumSize(new java.awt.Dimension(150, 24));
        campoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoSenha, gridBagConstraints);

        textoNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoNomeUsuario.setText("Nome de Usuário:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoNomeUsuario, gridBagConstraints);

        campoNomeUsuario.setPreferredSize(new java.awt.Dimension(200, 22));
        campoNomeUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeUsuarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoNomeUsuario, gridBagConstraints);

        textoEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoEmail.setText("E-mail:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoEmail, gridBagConstraints);

        campoEmail.setPreferredSize(new java.awt.Dimension(200, 22));
        campoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoEmailActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoEmail, gridBagConstraints);

        textoTelefone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoTelefone.setText("Telefone");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoTelefone, gridBagConstraints);

        campoTelefone.setPreferredSize(new java.awt.Dimension(200, 22));
        campoTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTelefoneActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoTelefone, gridBagConstraints);

        textoIdentificador.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoIdentificador.setText("CPF / CNPJ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoIdentificador, gridBagConstraints);

        campoIdentificador.setPreferredSize(new java.awt.Dimension(200, 22));
        campoIdentificador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoIdentificadorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoIdentificador, gridBagConstraints);

        textoCartaoCredito.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoCartaoCredito.setText("Cartão de Crédito:");
        if(!usuario.isAdmin()){
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.ipadx = 30;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
            painelCampos.add(textoCartaoCredito, gridBagConstraints);
        }

        campoCartao.setPreferredSize(new java.awt.Dimension(200, 22));
        campoCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCartaoActionPerformed(evt);
            }
        });
        if(!usuario.isAdmin()){
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
            painelCampos.add(campoCartao, gridBagConstraints);
        }

        textoRazaoSocial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoRazaoSocial.setText("Razão Social:");
        textoRazaoSocial.setMinimumSize(new java.awt.Dimension(100, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoRazaoSocial, gridBagConstraints);

        campoRazaoSocial.setMinimumSize(new java.awt.Dimension(90, 24));
        campoRazaoSocial.setPreferredSize(new java.awt.Dimension(200, 24));
        campoRazaoSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoRazaoSocialActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoRazaoSocial, gridBagConstraints);

        textoMatricula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoMatricula.setText("Matricula:");
        textoMatricula.setMinimumSize(new java.awt.Dimension(100, 18));
        textoMatricula.setPreferredSize(new java.awt.Dimension(100, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoMatricula, gridBagConstraints);

        campoMatricula.setEditable(false);
        campoMatricula.setMinimumSize(new java.awt.Dimension(100, 24));
        campoMatricula.setPreferredSize(new java.awt.Dimension(200, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoMatricula, gridBagConstraints);

        textoFuncaoFuncionario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoFuncaoFuncionario.setText("Função:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoFuncaoFuncionario, gridBagConstraints);

        textoDataDeNascimento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textoDataDeNascimento.setText("Data de Nascimento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(textoDataDeNascimento, gridBagConstraints);

        campoDataDeNascimento.setMinimumSize(new java.awt.Dimension(140, 24));
        campoDataDeNascimento.setPreferredSize(new java.awt.Dimension(170, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoDataDeNascimento, gridBagConstraints);

        campoFuncaoFuncionario.setEditable(false);
        campoFuncaoFuncionario.setMinimumSize(new java.awt.Dimension(200, 24));
        campoFuncaoFuncionario.setPreferredSize(new java.awt.Dimension(200, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCampos.add(campoFuncaoFuncionario, gridBagConstraints);

        painelCamposEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço"));
        painelCamposEndereco.setLayout(new java.awt.GridBagLayout());

        textoLogradouro.setText("Logradouro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoLogradouro, gridBagConstraints);

        campoLogradouro.setPreferredSize(new java.awt.Dimension(170, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoLogradouro, gridBagConstraints);

        textoNumero.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoNumero, gridBagConstraints);

        campoNumero.setText("S/N");
        campoNumero.setPreferredSize(new java.awt.Dimension(170, 22));
        campoNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNumeroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoNumero, gridBagConstraints);

        textoComplemento.setText("Complemento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoComplemento, gridBagConstraints);

        campoComplemento.setMinimumSize(new java.awt.Dimension(100, 24));
        campoComplemento.setPreferredSize(new java.awt.Dimension(170, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoComplemento, gridBagConstraints);

        textoBairro.setText("Bairro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoBairro, gridBagConstraints);

        campoBairro.setPreferredSize(new java.awt.Dimension(170, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoBairro, gridBagConstraints);

        textoCidade.setText("Cidade");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoCidade, gridBagConstraints);

        campoCidade.setPreferredSize(new java.awt.Dimension(170, 22));
        campoCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCidadeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoCidade, gridBagConstraints);

        textoUf.setText("UF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoUf, gridBagConstraints);

        campoUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        campoUf.setSelectedIndex(12);
        campoUf.setToolTipText("");
        campoUf.setMinimumSize(new java.awt.Dimension(100, 24));
        campoUf.setPreferredSize(new java.awt.Dimension(170, 22));
        campoUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoUfActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoUf, gridBagConstraints);

        textoCep.setText("CEP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(textoCep, gridBagConstraints);

        campoCep.setPreferredSize(new java.awt.Dimension(170, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        painelCamposEndereco.add(campoCep, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        painelCampos.add(painelCamposEndereco, gridBagConstraints);
        painelCamposEndereco.getAccessibleContext().setAccessibleDescription("");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        painelFormulario.add(painelCampos, gridBagConstraints);

        painelBotoes.setLayout(new java.awt.GridBagLayout());

        botaoVoltar.setText("Voltar");
        botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVoltarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 5);
        painelBotoes.add(botaoVoltar, gridBagConstraints);

        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        painelBotoes.add(botaoEditar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        painelFormulario.add(painelBotoes, gridBagConstraints);

        getContentPane().add(painelFormulario, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeActionPerformed

    private void campoSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoSenhaActionPerformed

    private void campoNomeUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeUsuarioActionPerformed

    private void campoEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoEmailActionPerformed

    private void campoIdentificadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoIdentificadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoIdentificadorActionPerformed

    private void campoTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoTelefoneActionPerformed

    private void campoCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCartaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCartaoActionPerformed

    private void campoCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCidadeActionPerformed

    private void campoUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoUfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoUfActionPerformed

    private void campoNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNumeroActionPerformed

    private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoVoltarActionPerformed

    private void campoRazaoSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoRazaoSocialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoRazaoSocialActionPerformed

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        this.editaUsuario();
    }//GEN-LAST:event_botaoEditarActionPerformed

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
            java.util.logging.Logger.getLogger(EdicaoUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EdicaoUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EdicaoUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EdicaoUsuario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EdicaoUsuario dialog = new EdicaoUsuario(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup botoesTipoPessoa;
    private javax.swing.JFormattedTextField campoBairro;
    private javax.swing.JFormattedTextField campoCartao;
    private javax.swing.JFormattedTextField campoCep;
    private javax.swing.JFormattedTextField campoCidade;
    private javax.swing.JFormattedTextField campoComplemento;
    private javax.swing.JFormattedTextField campoDataDeNascimento;
    private javax.swing.JFormattedTextField campoEmail;
    private javax.swing.JFormattedTextField campoFuncaoFuncionario;
    private javax.swing.JFormattedTextField campoIdentificador;
    private javax.swing.JFormattedTextField campoLogradouro;
    private javax.swing.JFormattedTextField campoMatricula;
    private javax.swing.JFormattedTextField campoNome;
    private javax.swing.JFormattedTextField campoNomeUsuario;
    private javax.swing.JFormattedTextField campoNumero;
    private javax.swing.JFormattedTextField campoRazaoSocial;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JComboBox<String> campoUf;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelCampos;
    private javax.swing.JPanel painelCamposEndereco;
    private javax.swing.JPanel painelFormulario;
    private javax.swing.JLabel textoBairro;
    private javax.swing.JLabel textoCartaoCredito;
    private javax.swing.JLabel textoCep;
    private javax.swing.JLabel textoCidade;
    private javax.swing.JLabel textoComplemento;
    private javax.swing.JLabel textoDataDeNascimento;
    private javax.swing.JLabel textoEmail;
    private javax.swing.JLabel textoFuncaoFuncionario;
    private javax.swing.JLabel textoIdentificador;
    private javax.swing.JLabel textoLogradouro;
    private javax.swing.JLabel textoMatricula;
    private javax.swing.JLabel textoNome;
    private javax.swing.JLabel textoNomeUsuario;
    private javax.swing.JLabel textoNumero;
    private javax.swing.JLabel textoRazaoSocial;
    private javax.swing.JLabel textoSenha;
    private javax.swing.JLabel textoTelefone;
    private javax.swing.JLabel textoUf;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
