import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaLogin extends LayoutJanela{
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private Jogador jogador;

    public JanelaLogin(){
        super("Login");
        criarTexto();
        criarBotao();
        setVisible(true);
    }

    private void criarTexto(){
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

        JLabel lblSubTitulo = new JLabel("Conecte-se a sua conta", SwingConstants.CENTER);
        lblSubTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        lblSubTitulo.setForeground(roxo);
        lblSubTitulo.setBounds(240, 250, 800, 40);
        add(lblSubTitulo);

        JLabel lblLogin = new JLabel("LOGIN:");
        lblLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblLogin.setForeground(roxo);
        lblLogin.setBounds(430, 320, 120, 36);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtLogin.setBounds(570, 320, 280, 36);
        add(txtLogin);

        JLabel lblSenha = new JLabel("SENHA:");
        lblSenha.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblSenha.setForeground(roxo);
        lblSenha.setBounds(430, 380, 120, 36);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtSenha.setBounds(570, 380, 280, 36);
        add(txtSenha);
    }

    private void criarBotao(){
        JButton btNovoCadastro = new JButton("Novo Cadastro");
        btNovoCadastro.setBounds(430,440,126,45);
        btNovoCadastro.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btNovoCadastro.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                new JanelaCadastroUsuario();
            }
        });
        add(btNovoCadastro);

        JButton btRecuperar = new JButton("Recuperar");
        btRecuperar.setBounds(576,440,126,45);
        btRecuperar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btRecuperar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaRecuperarSenhaEMAIL();
            }
        });
        add(btRecuperar);

        JButton btEntrar = new JButton("Entrar");
        btEntrar.setBounds(722,440,126,45);
        btEntrar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Persistencia persistencia = new Persistencia();
                CentralDeInformacoes central = persistencia.recuperarCentral();
                String login = txtLogin.getText();
                String senha = new String(txtSenha.getPassword());
                try{
                    jogador = central.recuperarJogadorPorLogin(login, senha);
                    dispose();
                    if(jogador.getGrau()==Grau.ADM){
                        new JanelaPrincipalJogoADM(jogador);
                    } else{
                        new JanelaPrincipalJogo(jogador);
                    }
                } catch (ContaNaoEncontradaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Atenção", JOptionPane.WARNING_MESSAGE);
                } catch (CamposVaziosException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btEntrar);

        JButton btSair = new JButton("Sair");
        btSair.setBounds(10,660,80,50);
        btSair.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btSair);
    }
    public static void main(String[] args){
        new JanelaLogin();
    }
}
