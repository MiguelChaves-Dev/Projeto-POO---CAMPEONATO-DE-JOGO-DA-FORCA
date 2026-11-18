import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;
import Exceptions.PalavrasIndisponiveisException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaJogo extends LayoutJanela{
    private JTextField txtPalpite;
    private Jogador jogador;
    private Dificuldade dificuldade;
    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();
    private Forca forca;
    private JLabel lblPalavraOculta;
    private JLabel lblErros;

    public JanelaJogo(Jogador j, Dificuldade d){
        super("Campeonato de Forca");
        jogador = j;
        dificuldade = d;
        try {
            forca = new Forca(central.sortearPalavra(jogador, dificuldade));
        } catch (PalavrasIndisponiveisException | CamposVaziosException e) {
            dispose();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
            new JanelaDificuldade(jogador);
            return;
        }
        criarTexto();
        criarBotao();
        setVisible(true);
    }
    private void criarTexto(){
        Color roxo = new Color(110,70,200);

        lblPalavraOculta = new JLabel(forca.getPalavraOculta(), SwingConstants.CENTER);
        lblPalavraOculta.setFont(new Font("Comic Sans MS", Font.BOLD, 52));
        lblPalavraOculta.setForeground(roxo);
        lblPalavraOculta.setBounds(280, 60, 950, 120);
        add(lblPalavraOculta);

        lblErros = new JLabel("Erros: 0 / 6", SwingConstants.CENTER);
        lblErros.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblErros.setForeground(Color.RED);
        lblErros.setBounds(280, 190, 950, 40);
        add(lblErros);

        JLabel lblPalpite = new JLabel("PALPITE:");
        lblPalpite.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblPalpite.setForeground(roxo);
        lblPalpite.setBounds(500, 320, 130, 36);
        add(lblPalpite);

        txtPalpite = new JTextField();
        txtPalpite.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtPalpite.setBounds(640, 320, 280, 36);
        add(txtPalpite);
    }

    private void criarBotao(){
        JButton btEnviar = new JButton("Chutar");
        btEnviar.setBounds(722, 440, 126, 45);
        btEnviar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String palpiteGrande = txtPalpite.getText().trim();

                    if (palpiteGrande.isEmpty()) {
                        throw new CamposVaziosException();
                    }

                    if (palpiteGrande.length() > 1) {
                        JOptionPane.showMessageDialog(null, "Insira apenas uma letra!", "Atenção", JOptionPane.WARNING_MESSAGE);
                        txtPalpite.setText("");
                    } else {
                        char palpite = palpiteGrande.charAt(0);

                        forca.tentarLetra(palpite, jogador);

                        txtPalpite.setText("");
                        lblPalavraOculta.setText(forca.getPalavraOculta());
                        lblErros.setText("Erros: " + forca.getErros() + " / 6");

                        if (forca.isVenceu() || forca.isPerdeu()) {
                            finalizarPartida();
                        }
                    }
                } catch (CamposVaziosException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btEnviar);
    }

    private void finalizarPartida() {
        int pontos = forca.calcularPontuacao();
        String resultado;

        if (forca.isVenceu()) {
            resultado = "VITORIA";
        } else {
            resultado = "DERROTA";
        }

        Jogador jogadorDaCentral;
        try{
            jogadorDaCentral = central.recuperarJogadorPorEmail(jogador.getEmail());
            if (jogadorDaCentral == null) {
                jogadorDaCentral = jogador;
            }
        } catch (ContaNaoEncontradaException | CamposVaziosException ex){
            JOptionPane.showMessageDialog(null,"Não foi possivel salvar a Partida!","Atenção",JOptionPane.WARNING_MESSAGE);
            jogadorDaCentral = null;
        }

        Partida partida = new Partida(
                jogadorDaCentral,
                forca.getPalavra(),
                dificuldade,
                forca.getLogsDaRodada(),
                pontos,
                resultado
        );

        jogadorDaCentral.somarPontuacaoSolo(pontos);
        jogadorDaCentral.getPalavrasJogadas().add(forca.getPalavra());
        central.adicionarPartida(partida);
        persistencia.salvarCentral(central);

        if (forca.isVenceu()) {
            JOptionPane.showMessageDialog(null, "Parabéns! Você venceu e ganhou " + pontos + " pontos!");
        } else {
            JOptionPane.showMessageDialog(null, "Você perdeu! A palavra era: " + forca.getPalavra().getPalavra() + "\nPontuação: " + pontos);
        }

        dispose();
        new JanelaDificuldade(jogadorDaCentral);
    }
}
