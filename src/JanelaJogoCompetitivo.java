import Exceptions.CamposVaziosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JanelaJogoCompetitivo extends LayoutJanela{
    private Jogador jogador1;
    private Jogador jogador2;
    private Dificuldade dificuldade;
    private ArrayList<Palavra> palavrasDuelo;

    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();

    private Forca forca;
    private int rodadaAtual = 1; // 1 ou 2
    private Jogador jogadorDaVez;
    private boolean segundoJogadorJogouNaRodada = false;

    private int pontosConfrontoJ1 = 0;
    private int pontosConfrontoJ2 = 0;
    private ArrayList<HistoricoPalpites> logsConfronto = new ArrayList<>();

    private JLabel lblRodada;
    private JLabel lblTurno;
    private JLabel lblPlacar;
    private JLabel lblPalavraOculta;
    private JLabel lblErros;
    private JTextField txtPalpite;

    public JanelaJogoCompetitivo(Jogador j1, Jogador j2, Dificuldade d, ArrayList<Palavra> palavras) {
        super("Campeonato de Forca - Competitivo");
        this.jogador1 = j1;
        this.jogador2 = j2;
        this.dificuldade = d;
        this.palavrasDuelo = palavras;

        // Na 1ª partida, o Jogador 1 começa
        this.jogadorDaVez = jogador1;
        this.forca = new Forca(palavrasDuelo.get(0));

        criarTexto();
        criarBotao();
        setVisible(true);
    }

    private void criarTexto() {
        Color roxo = new Color(110, 70, 200);

        lblRodada = new JLabel("PARTIDA 1 DE 2", SwingConstants.CENTER);
        lblRodada.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        lblRodada.setForeground(roxo);
        lblRodada.setBounds(240, 20, 800, 40);
        add(lblRodada);



        lblPalavraOculta = new JLabel(forca.getPalavraOculta(), SwingConstants.CENTER);
        lblPalavraOculta.setFont(new Font("Comic Sans MS", Font.BOLD, 52));
        lblPalavraOculta.setForeground(roxo);
        lblPalavraOculta.setBounds(240, 60, 800, 80);
        add(lblPalavraOculta);

        lblErros = new JLabel("Erros da palavra: 0 / 6", SwingConstants.CENTER);
        lblErros.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblErros.setForeground(Color.RED);
        lblErros.setBounds(240, 140, 800, 40);
        add(lblErros);

        lblTurno = new JLabel("Vez de: " + jogadorDaVez.getLogin(), SwingConstants.CENTER);
        lblTurno.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        lblTurno.setForeground(new Color(0, 140, 70));
        lblTurno.setBounds(240, 190, 800, 45);
        add(lblTurno);

        lblPlacar = new JLabel(jogador1.getLogin() + ": 0 pts   |   " + jogador2.getLogin() + ": 0 pts", SwingConstants.CENTER);
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
        JButton btEnviar = new JButton("Chutar");
        btEnviar.setBounds(722,380,126,45);
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

                    // Processa a tentativa para o jogador da vez
                    boolean acertou = forca.tentarLetra(palpite, jogadorDaVez);

                    lblPalavraOculta.setText(forca.getPalavraOculta());
                    lblErros.setText("Erros da palavra: " + forca.getErros() + " / 6");

                    // Verifica se a palavra atual terminou (vitória ou 6 erros)
                    if (forca.isVenceu() || forca.isPerdeu()) {
                        encerrarRodada();
                    } else if (!acertou) {
                        // Regra: jogador continua dando palpites até errar, quando passa a vez para o próximo
                        alternarTurno();
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

    private void alternarTurno() {
        segundoJogadorJogouNaRodada = true;

        if (jogadorDaVez.getLogin().equalsIgnoreCase(jogador1.getLogin())) {
            jogadorDaVez = jogador2;
        } else {
            jogadorDaVez = jogador1;
        }

        lblTurno.setText("Vez de: " + jogadorDaVez.getLogin());
        JOptionPane.showMessageDialog(null, "Letra errada! Passou a vez para " + jogadorDaVez.getLogin() + "!");
    }

    private void encerrarRodada() {
        // Guarda os logs desta rodada no histórico geral do confronto[cite: 1]
        logsConfronto.addAll(forca.getLogsDaRodada());

        int pontosRodada = forca.calcularPontuacao();

        if (forca.isVenceu()) {
            // Quem acertou a última letra venceu a rodada[cite: 1]
            if (jogadorDaVez.getLogin().equalsIgnoreCase(jogador1.getLogin())) {
                pontosConfrontoJ1 += pontosRodada;
                // Regra: se o 2º jogador não teve chance de dizer uma letra, recebe 100 pontos[cite: 1]
                if (!segundoJogadorJogouNaRodada) {
                    pontosConfrontoJ2 += 100;
                }
            } else {
                pontosConfrontoJ2 += pontosRodada;
                if (!segundoJogadorJogouNaRodada) {
                    pontosConfrontoJ1 += 100;
                }
            }

            JOptionPane.showMessageDialog(null, jogadorDaVez.getLogin() + " completou a palavra '" +
                    forca.getPalavra().getPalavra() + "' e ganhou " + pontosRodada + " pontos!");
        } else {
            // Atingiu 6 erros (derrota na palavra)[cite: 1]
            JOptionPane.showMessageDialog(null, "Ninguém acertou! A palavra era: " + forca.getPalavra().getPalavra());
        }

        // Atualiza placar na tela
        lblPlacar.setText(jogador1.getLogin() + ": " + pontosConfrontoJ1 + " pts   |   " + jogador2.getLogin() + ": " + pontosConfrontoJ2 + " pts");

        if (rodadaAtual == 1) {
            // Prepara a 2ª partida do confronto[cite: 1]
            rodadaAtual = 2;
            segundoJogadorJogouNaRodada = false;

            // Regra: cada partida é iniciada por um jogador diferente (na 2ª começa o jogador2)[cite: 1]
            jogadorDaVez = jogador2;
            forca = new Forca(palavrasDuelo.get(1));

            lblRodada.setText("PARTIDA 2 DE 2");
            lblTurno.setText("Vez de: " + jogadorDaVez.getLogin());
            lblPalavraOculta.setText(forca.getPalavraOculta());
            lblErros.setText("Erros da palavra: 0 / 6");
        } else {
            // Acabaram as duas partidas: finaliza o confronto e salva na Central[cite: 1]
            try {
                finalizarConfronto();
            }catch (CamposVaziosException ex){
                //Isso não vai acontecer, é só um tratamento de erro para o recuperar jogador por email aconteça
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        }
    }

    private void finalizarConfronto() throws CamposVaziosException{
        Jogador j1Central = central.recuperarJogadorPorEmail(jogador1.getEmail());
        Jogador j2Central = central.recuperarJogadorPorEmail(jogador2.getEmail());

        String resultadoFinal;
        String mensagemFinal;

        // Regra do ranque competitivo: 3 pontos por vitória, 1 ponto por empate[cite: 1]
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

        // Marca as 2 palavras como já jogadas para AMBOS os jogadores não repetirem no futuro[cite: 1]
        for (Palavra p : palavrasDuelo) {
            j1Central.getPalavrasJogadas().add(p);
            j2Central.getPalavrasJogadas().add(p);
        }

        // Cria o objeto Partida para salvar no histórico
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
