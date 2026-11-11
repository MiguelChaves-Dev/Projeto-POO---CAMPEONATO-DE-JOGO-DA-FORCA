import Exceptions.CamposVaziosException;
import Exceptions.ContaRepetidaException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.itextpdf.text.xml.xmp.DublinCoreProperties.setTitle;

public class JanelaCadastroADM extends LayoutJanela{
    private JTextField txtLogin;
    private JTextField txtEmail;
    private JPasswordField txtSenha;

    public JanelaCadastroADM() {
         super("CadastroADM");
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

        JLabel lblCadastro = new JLabel("CADASTRO ADM", SwingConstants.CENTER);
        lblCadastro.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        lblCadastro.setForeground(roxo);
        lblCadastro.setBounds(240, 250, 800, 40);
        add(lblCadastro);

        JLabel lblLogin = new JLabel("LOGIN:");
        lblLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblLogin.setForeground(roxo);
        lblLogin.setBounds(430, 320, 120, 36);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtLogin.setBounds(570, 320, 280, 36);
        add(txtLogin);

        JLabel lblEmail = new JLabel("EMAIL:");
        lblEmail.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblEmail.setForeground(roxo);
        lblEmail.setBounds(430, 380, 120, 36);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtEmail.setBounds(570, 380, 280, 36);
        add(txtEmail);

        JLabel lblSenha = new JLabel("SENHA:");
        lblSenha.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblSenha.setForeground(roxo);
        lblSenha.setBounds(430, 440, 120, 36);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtSenha.setBounds(570, 440, 280, 36);
        add(txtSenha);
    }

    private void criarBotao(){
        JButton btCadastrar = new JButton("CADASTRAR");
        btCadastrar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btCadastrar.setBounds(540, 530, 200, 45);
        btCadastrar.addActionListener(new ActionListener(){
            private void validarCampos(String login, String email, String senha) throws CamposVaziosException, ContaRepetidaException {
                Persistencia persistencia = new Persistencia();
                CentralDeInformacoes central = persistencia.recuperarCentral();
                if(login.isBlank() || email.isBlank() || senha.isBlank()){
                    throw new CamposVaziosException();
                }
                if (!central.adicionarJogador(new Jogador(login, email, senha, Grau.ADM))){
                    throw new ContaRepetidaException();
                }
                persistencia.salvarCentral(central);

            }
            public void actionPerformed(ActionEvent e){
                String login = txtLogin.getText();
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());
                try{
                    validarCampos(login, email, senha);
                    JOptionPane.showMessageDialog(null, "ADM cadastrado com sucesso!");
                    dispose();
                    new JanelaLogin();
                } catch (CamposVaziosException | ContaRepetidaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btCadastrar);
    }

    public static void main(String[] args) {
        new JanelaCadastroADM();
    }
}
