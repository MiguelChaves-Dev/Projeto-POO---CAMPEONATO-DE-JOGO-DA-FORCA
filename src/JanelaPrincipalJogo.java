import Exceptions.CamposVaziosException;
import Exceptions.ContaRepetidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipalJogo extends LayoutJanela{
    private Jogador jogador;
    public JanelaPrincipalJogo(Jogador j){
        super("Campeonato de Forca");
        jogador = j;
        criarTexto();
        criarBotao();
        setVisible(true);
    }

    private void criarTexto() {
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
    }

    private void criarBotao(){
        JButton btEstatistica = new JButton("AAA");
        btEstatistica.setBounds(1200, 20, 60, 60);
        btEstatistica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Aqui o usuário (nesse caso o usuário: "+ jogador.getLogin() + " ) verá suas estatísticas!");
            }
        });
        add(btEstatistica);

        JButton btJogar = new JButton("Jogar");
        btJogar.setBounds(430,320,420,96);
        btJogar.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        btJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaModoDeJogo(jogador);
            }
        });
        add(btJogar);

        JButton btTrocar = new JButton("Trocar Usuario");
        btTrocar.setBounds(430,446,200,45);
        btTrocar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btTrocar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaLogin();
            }
        });
        add(btTrocar);

        JButton btSair = new JButton("Sair");
        btSair.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btSair.setBounds(650, 446, 200, 45);
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btSair);
    }
}
