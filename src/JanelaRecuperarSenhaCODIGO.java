import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaRecuperarSenhaCODIGO extends LayoutJanela{
    private JTextField txtCodigo;
    private RecuperacaoDeSenha recuperar;
    private Jogador jogador;
    public JanelaRecuperarSenhaCODIGO(RecuperacaoDeSenha r, Jogador j){
        super("Recuperação de Conta");
        recuperar = r;
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

        JLabel lblSubTitulo = new JLabel("Insira o Código de Verificação", SwingConstants.CENTER);
        lblSubTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        lblSubTitulo.setForeground(roxo);
        lblSubTitulo.setBounds(240, 250, 800, 40);
        add(lblSubTitulo);

        JLabel lblCodigo = new JLabel("CÓDIGO:");
        lblCodigo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblCodigo.setForeground(roxo);
        lblCodigo.setBounds(430, 320, 120, 36);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtCodigo.setBounds(570, 320, 280, 36);
        add(txtCodigo);
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
                String codigo = txtCodigo.getText();
                try{
                    if(recuperar.testarCodigo(codigo)){
                        dispose();
                        new JanelaRecuperarSenhaSENHA(jogador);
                    } else{
                        JOptionPane.showMessageDialog(null, "Código inválido, tente novamente.","Atenção", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ContaNaoEncontradaException ex){
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado!","Atenção", JOptionPane.WARNING_MESSAGE);
                } catch (CamposVaziosException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btProximo);
    }
}
