import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaModoDeJogo extends LayoutJanela{
    private Jogador jogador;
    public JanelaModoDeJogo(Jogador j){
        super("Campeonato de Forca");
        jogador = j;
        criarTexto();
        criarBotao();
        setVisible(true);
    }
    private void criarTexto(){
        Color roxo = new Color(110,70,200);

        JLabel lblTitulo = new JLabel("SELECIONE O", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        lblTitulo.setForeground(roxo);
        lblTitulo.setBounds(240, 60, 800, 80);
        add(lblTitulo);

        JLabel lblTitulo2 = new JLabel("MODO DE JOGO", SwingConstants.CENTER);
        lblTitulo2.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        lblTitulo2.setForeground(roxo);
        lblTitulo2.setBounds(240, 140, 800, 80);
        add(lblTitulo2);
    }

    private void criarBotao(){
        JButton btSolo = new JButton("Modo Solo");
        btSolo.setBounds(430,295,195,221);
        btSolo.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        add(btSolo);

        JButton btComp = new JButton("Modo Competitivo");
        btComp.setBounds(655,295,195,221);
        btComp.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        add(btComp);

        JButton btVoltar = new JButton("Voltar");
        btVoltar.setBounds(10,660,80,50);
        btVoltar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if(jogador.getIsADM()==true){
                    new JanelaPrincipalJogoADM(jogador);
                } else{
                    new JanelaPrincipalJogo(jogador);
                }
            }
        });
        add(btVoltar);
    }

}
