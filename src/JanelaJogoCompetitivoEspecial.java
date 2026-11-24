import Exceptions.CamposVaziosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JanelaJogoCompetitivoEspecial extends LayoutJanela {
    private Jogador jogador1;
    private Jogador jogador2;
    private Dificuldade dificuldade;
    private ArrayList<Palavra> palavrasDuelo = new ArrayList<>();

    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();

    private Forca forca1; // Palavra escolhida pelo J2 para o J1 adivinhar
    private Forca forca2; // Palavra escolhida pelo J1 para o J2 adivinhar
    private Jogador jogadorDaVez;

    private int pontosConfrontoJ1 = 0;
    private int pontosConfrontoJ2 = 0;
    private ArrayList<HistoricoPalpites> logsConfronto = new ArrayList<>();

    private JLabel lblRodada;
    private JLabel lblTurno;
    private JLabel lblPlacar;
    private JLabel lblPalavraOculta;
    private JLabel lblErros;
    private JTextField txtPalpite;

    // p1 = palavra escolhida pelo J1 (vai para forca2)
    // p2 = palavra escolhida pelo J2 (vai para forca1)
    public JanelaJogoCompetitivoEspecial(Jogador j1, Jogador j2, Dificuldade d, Palavra p1, Palavra p2) {
        super("Campeonato de Forca - Modo Especial");
        this.jogador1 = j1;
        this.jogador2 = j2;
        this.dificuldade = d;

        this.palavrasDuelo.add(p1);
        this.palavrasDuelo.add(p2);

        this.forca1 = new Forca(p2);
        this.forca2 = new Forca(p1);

        // O Jogador 1 começa tentando a palavra dele (forca1)
        this.jogadorDaVez = jogador1;

        criarTexto();
        criarBotao();
        atualizarInterface();
        setVisible(true);
    }

    private void criarTexto() {
        Color roxo = new Color(110, 70, 200);

        lblRodada = new JLabel("MODO ESPECIAL - DUELO DE PALAVRAS", SwingConstants.CENTER);
        lblRodada.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        lblRodada.setForeground(roxo);
        lblRodada.setBounds(240, 20, 800, 40);
        add(lblRodada);

        lblPalavraOculta = new JLabel("", SwingConstants.CENTER);
        lblPalavraOculta.setFont(new Font("Comic Sans MS", Font.BOLD, 52));
        lblPalavraOculta.setForeground(roxo);
        lblPalavraOculta.setBounds(0, 60, 1280, 80);
        add(lblPalavraOculta);

        lblErros = new JLabel("Erros: 0 / 6", SwingConstants.CENTER);
        lblErros.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblErros.setForeground(Color.RED);
        lblErros.setBounds(240, 140, 800, 40);
        add(lblErros);

        lblTurno = new JLabel("", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        lblTurno.setForeground(new Color(0, 140, 70));
        lblTurno.setBounds(240, 190, 800, 45);
        add(lblTurno);

        lblPlacar = new JLabel("", SwingConstants.CENTER);
        lblPlacar.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblPlacar.setForeground(Color.DARK_GRAY);
        lblPlacar.setBounds(240, 235, 800, 30);
        add(lblPlacar);

        JLabel lblPalpite = new JLabel("PALPITE:");
        lblPalpite.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblPalpite.setForeground(roxo);
        lblPalpite.setBounds(430, 320, 120, 36);
        add(lblPalpite);

        txtPalpite = new JTextField();
        txtPalpite.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtPalpite.setBounds(570, 320, 280, 36);
        add(txtPalpite);
    }

    private void criarBotao() {
        JButton btDica = new JButton("Dica");
        btDica.setBounds(430,380,126,45);
        btDica.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btDica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Forca forcaAtual = getForcaAtual();
                JOptionPane.showMessageDialog(null,forcaAtual.getPalavra().getDica(),"Dica:",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(btDica);

        JButton btEnviar = new JButton("Chutar");
        btEnviar.setBounds(722, 380, 126, 45);
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
                        return;
                    }

                    char palpite = palpiteGrande.charAt(0);
                    txtPalpite.setText("");

                    // Pega a instância de Forca correspondente ao jogador atual
                    Forca forcaAtual = getForcaAtual();
                    boolean acertou = forcaAtual.tentarLetra(palpite, jogadorDaVez);

                    atualizarInterface();

                    // Verifica se a palavra do jogador atual chegou ao fim (venceu ou 6 erros)
                    if (isForcaFinalizada(forcaAtual)) {
                        processarFimIndividual(forcaAtual);
                    } else if (!acertou) {
                        // Errou e ainda tem tentativas: passa a vez se o colega ainda estiver jogando
                        alternarTurnoSePossivel();
                    }

                } catch (CamposVaziosException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btEnviar);

        JButton btVoltar = new JButton("Desistir");
        btVoltar.setBounds(10, 660, 90, 50);
        btVoltar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaModoDeJogo(jogador1);
            }
        });
        add(btVoltar);
    }

    private Forca getForcaAtual() {
        if (jogadorDaVez.getLogin().equalsIgnoreCase(jogador1.getLogin())) {
            return forca1;
        } else {
            return forca2;
        }
    }

    private boolean isForcaFinalizada(Forca f) {
        return f.isVenceu() || f.isPerdeu();
    }

    private void atualizarInterface() {
        Forca forcaAtual = getForcaAtual();
        lblPalavraOculta.setText(forcaAtual.getPalavraOculta());
        lblErros.setText("Erros de " + jogadorDaVez.getLogin() + ": " + forcaAtual.getErros() + " / 6");
        lblTurno.setText("Vez de: " + jogadorDaVez.getLogin());
        lblPlacar.setText(jogador1.getLogin() + ": " + pontosConfrontoJ1 + " pts   |   " + jogador2.getLogin() + ": " + pontosConfrontoJ2 + " pts");
    }

    private void alternarTurnoSePossivel() {
        boolean ehJ1 = jogadorDaVez.getLogin().equalsIgnoreCase(jogador1.getLogin());
        Forca forcaOponente;
        Jogador proximoJogador;

        if (ehJ1) {
            forcaOponente = forca2;
            proximoJogador = jogador2;
        } else {
            forcaOponente = forca1;
            proximoJogador = jogador1;
        }

        // Só troca a vez se o oponente ainda não tiver terminado a palavra dele
        if (!isForcaFinalizada(forcaOponente)) {
            jogadorDaVez = proximoJogador;
            atualizarInterface();
            JOptionPane.showMessageDialog(null, "Letra errada! Passou a vez para " + jogadorDaVez.getLogin() + "!");
        } else {
            JOptionPane.showMessageDialog(null, "Letra errada! Como " + proximoJogador.getLogin() +
                    " já finalizou a palavra dele, continue tentando!");
        }
    }

    private void processarFimIndividual(Forca forcaConcluida) {
        boolean ehJ1 = jogadorDaVez.getLogin().equalsIgnoreCase(jogador1.getLogin());
        int pontos = forcaConcluida.calcularPontuacao();

        if (forcaConcluida.isVenceu()) {
            if (ehJ1) {
                pontosConfrontoJ1 += pontos;
            } else {
                pontosConfrontoJ2 += pontos;
            }
            JOptionPane.showMessageDialog(null, jogadorDaVez.getLogin() + " acertou a palavra '" +
                    forcaConcluida.getPalavra().getPalavra() + "' e ganhou " + pontos + " pontos!");
        } else {
            JOptionPane.showMessageDialog(null, jogadorDaVez.getLogin() + " atingiu 6 erros! A palavra era: " +
                    forcaConcluida.getPalavra().getPalavra());
        }

        atualizarInterface();

        // Verifica se AMBOS já encerraram suas palavras para finalizar o confronto
        if (isForcaFinalizada(forca1) && isForcaFinalizada(forca2)) {
            logsConfronto.addAll(forca1.getLogsDaRodada());
            logsConfronto.addAll(forca2.getLogsDaRodada());
            try {
                finalizarConfronto();
            } catch (CamposVaziosException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            // Se apenas um acabou, passa definitivamente a vez para o que falta terminar
            if (ehJ1) {
                jogadorDaVez = jogador2;
            } else {
                jogadorDaVez = jogador1;
            }
            atualizarInterface();
            JOptionPane.showMessageDialog(null, "Agora é a vez de " + jogadorDaVez.getLogin() + " finalizar a palavra dele!");
        }
    }

    private void finalizarConfronto() throws CamposVaziosException {
        Jogador j1Central = central.recuperarJogadorPorEmail(jogador1.getEmail());
        Jogador j2Central = central.recuperarJogadorPorEmail(jogador2.getEmail());

        String resultadoFinal;
        String mensagemFinal;

        if (pontosConfrontoJ1 > pontosConfrontoJ2) {
            j1Central.somarPontuacaoComp(3);
            resultadoFinal = "VITORIA_" + j1Central.getLogin();
            mensagemFinal = "Vencedor do confronto: " + j1Central.getLogin() + " (+3 pts no ranque)!";
        } else if (pontosConfrontoJ2 > pontosConfrontoJ1) {
            j2Central.somarPontuacaoComp(3);
            resultadoFinal = "VITORIA_" + j2Central.getLogin();
            mensagemFinal = "Vencedor do confronto: " + j2Central.getLogin() + " (+3 pts no ranque)!";
        } else {
            j1Central.somarPontuacaoComp(1);
            j2Central.somarPontuacaoComp(1);
            resultadoFinal = "EMPATE";
            mensagemFinal = "O confronto terminou EMPATADO (+1 pt no ranque para cada)!";
        }

        // Marca apenas a palavra que cada um efetivamente jogou no histórico individual
        j1Central.getPalavrasJogadas().add(forca1.getPalavra());
        j2Central.getPalavrasJogadas().add(forca2.getPalavra());

        Partida partida = new Partida();
        partida.setDataHora(LocalDateTime.now());
        partida.setCompetitiva(true);
        partida.setDificuldade(dificuldade);
        partida.getJogadores().add(j1Central);
        partida.getJogadores().add(j2Central);
        partida.setPalavras(palavrasDuelo);
        partida.setLogsOperacoes(logsConfronto);
        partida.setPontosJogador1(pontosConfrontoJ1);
        partida.setPontosJogador2(pontosConfrontoJ2);
        partida.setResultado(resultadoFinal);

        central.adicionarPartida(partida);
        persistencia.salvarCentral(central);

        JOptionPane.showMessageDialog(null,
                "FIM DO CONFRONTO!\n\n" +
                        j1Central.getLogin() + ": " + pontosConfrontoJ1 + " pontos\n" +
                        j2Central.getLogin() + ": " + pontosConfrontoJ2 + " pontos\n\n" +
                        mensagemFinal);

        dispose();
        new JanelaModoDeJogo(j1Central);
    }
}