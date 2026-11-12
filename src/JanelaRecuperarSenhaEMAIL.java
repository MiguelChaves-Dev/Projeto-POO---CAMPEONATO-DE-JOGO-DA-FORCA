import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;
import Exceptions.ContaRepetidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaRecuperarSenhaEMAIL extends LayoutJanela{
    private JTextField txtEmail;

    public JanelaRecuperarSenhaEMAIL(){
        super("Recuperação de Conta");
        criarTitulo();
        criarBotao();
        setVisible(true);
    }
    private void criarTitulo(){
        Color roxo = new Color(110, 70, 200);
        JLabel lblTitulo1 = new JLabel("CAMPEONATO", SwingConstants.CENTER);
        lblTitulo1.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        lblTitulo1.setForeground(roxo);
        lblTitulo1.setBounds(240, 60, 800, 80);
        add(lblTitulo1);

        JLabel lblTitulo2 = new JLabel("DE FORCA", SwingConstants.CENTER);
        lblTitulo2.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        lblTitulo2.setForeground(roxo);
        lblTitulo2.setBounds(240, 140, 800, 80);
        add(lblTitulo2);

        JLabel lblSubTitulo = new JLabel("Insira o Email da Conta", SwingConstants.CENTER);
        lblSubTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        lblSubTitulo.setForeground(roxo);
        lblSubTitulo.setBounds(240, 250, 800, 40);
        add(lblSubTitulo);

        JLabel lblEmail = new JLabel("EMAIL:");
        lblEmail.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblEmail.setForeground(roxo);
        lblEmail.setBounds(430, 320, 120, 36);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtEmail.setBounds(570, 320, 280, 36);
        add(txtEmail);
    }

    private void criarBotao(){
        JButton btCancelar = new JButton("Cancelar");
        btCancelar.setBounds(430,386,200,45);
        btCancelar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaLogin();
            }
        });
        add(btCancelar);

        JButton btProximo = new JButton("Próximo");
        btProximo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btProximo.setBounds(650, 386, 200, 45);
        btProximo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Persistencia persistencia = new Persistencia();
                CentralDeInformacoes central = persistencia.recuperarCentral();
                String email = txtEmail.getText();
                RecuperacaoDeSenha recuperar = new RecuperacaoDeSenha();
                recuperar.gerarCodigo();
                try{
                    Jogador jogador = central.recuperarJogadorPorEmail(email);
                    Mensageiro.enviarMensagem(jogador.getEmail(), "Código de Recuperação de Senha", "Seu código de verificação é: " + recuperar.getCodigo());
                    dispose();
                    new JanelaRecuperarSenhaCODIGO(recuperar, jogador);
                } catch (ContaNaoEncontradaException ex){
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado!","Atenção", JOptionPane.WARNING_MESSAGE);
                } catch (CamposVaziosException ex){
                    JOptionPane.showMessageDialog(null, "ex.getMessage()", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btProximo);
    }
}
