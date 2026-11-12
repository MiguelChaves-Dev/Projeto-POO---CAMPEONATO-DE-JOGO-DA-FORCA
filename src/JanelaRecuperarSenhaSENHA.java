import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaRecuperarSenhaSENHA extends LayoutJanela{
    private JPasswordField txtSenha;
    private Jogador jogador;

    public JanelaRecuperarSenhaSENHA(Jogador j){
        super("Recuperação de Conta");
        jogador = j;
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

        JLabel lblSubTitulo = new JLabel("Insira a sua nova Senha", SwingConstants.CENTER);
        lblSubTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        lblSubTitulo.setForeground(roxo);
        lblSubTitulo.setBounds(240, 250, 800, 40);
        add(lblSubTitulo);

        JLabel lblSenha = new JLabel("SENHA:");
        lblSenha.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblSenha.setForeground(roxo);
        lblSenha.setBounds(430, 320, 120, 36);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtSenha.setBounds(570, 320, 280, 36);
        add(txtSenha);
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
                String senha = new String(txtSenha.getPassword());
                try{
                    central.trocarSenhaJogador(jogador, senha);
                    JOptionPane.showMessageDialog(null, "Senha alterada com Sucesso!");
                    persistencia.salvarCentral(central);
                    dispose();
                    new JanelaLogin();
                } catch (CamposVaziosException ex){
                    JOptionPane.showMessageDialog(null, "ex.getMessage()", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btProximo);
    }
}
