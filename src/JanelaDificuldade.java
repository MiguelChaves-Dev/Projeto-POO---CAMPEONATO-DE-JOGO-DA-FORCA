import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaDificuldade extends LayoutJanela{
    private Jogador jogador;
    public JanelaDificuldade(Jogador j){
        super("Campeonato de Forca");
        jogador = j;
        criarTexto();
        criarBotao();
        setVisible(true);
    }
    private void criarTexto(){
        Color roxo = new Color(110,70,200);

        JLabel lblTitulo = new JLabel("SELECIONE A", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        lblTitulo.setForeground(roxo);
        lblTitulo.setBounds(240, 60, 800, 80);
        add(lblTitulo);

        JLabel lblTitulo2 = new JLabel("DIFICULDADE DO JOGO", SwingConstants.CENTER);
        lblTitulo2.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        lblTitulo2.setForeground(roxo);
        lblTitulo2.setBounds(240, 140, 800, 80);
        add(lblTitulo2);
    }

    private void criarBotao(){
        JButton btFacil = new JButton("Facil");
        btFacil.setBounds(328,295,195,221);
        btFacil.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        btFacil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaJogo(jogador, Dificuldade.FACIL);
            }
        });
        add(btFacil);

        JButton btMedio = new JButton("Médio");
        btMedio.setBounds(543,295,195,221);
        btMedio.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        btMedio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaJogo(jogador, Dificuldade.MEDIO);
            }
        });
        add(btMedio);

        JButton btDificil = new JButton("Difícil");
        btDificil.setBounds(758, 295, 195, 221);
        btDificil.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        btDificil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaJogo(jogador, Dificuldade.DIFICIL);
            }
        });
        add(btDificil);

        JButton btVoltar = new JButton("Voltar");
        btVoltar.setBounds(10,660,80,50);
        btVoltar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaModoDeJogo(jogador);
            }
        });
        add(btVoltar);
    }
}
