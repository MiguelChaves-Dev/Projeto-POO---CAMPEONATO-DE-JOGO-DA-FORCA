import Exceptions.CamposVaziosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaSelecaoPalavrasEspecial extends LayoutJanela {
    private Jogador jogador1;
    private Jogador jogador2;
    private Dificuldade dificuldade;

    private Palavra palavraDoJ1; // Palavra escolhida pelo J1 (para o J2 adivinhar)
    private Palavra palavraDoJ2; // Palavra escolhida pelo J2 (para o J1 adivinhar)
    private boolean vezDoJogador1 = true;

    private JLabel lblTitulo;
    private JLabel lblInstrucao;
    private JLabel lblAviso;
    private JPasswordField txtPalavraSecreta;
    private JTextField txtDica;
    private JCheckBox chkMostrarPalavra;
    private JButton btConfirmar;

    public JanelaSelecaoPalavrasEspecial(Jogador j1, Jogador j2, Dificuldade d) {
        super("Campeonato de Forca - Seleção de Palavras");
        this.jogador1 = j1;
        this.jogador2 = j2;
        this.dificuldade = d;

        criarComponentes();
        atualizarTelaParaJogadorAtual();
        setVisible(true);
    }

    private void criarComponentes() {
        Color roxo = new Color(110, 70, 200);

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblTitulo.setForeground(roxo);
        lblTitulo.setBounds(140, 50, 1000, 45);
        add(lblTitulo);

        lblInstrucao = new JLabel("", SwingConstants.CENTER);
        lblInstrucao.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblInstrucao.setForeground(new Color(0, 140, 70));
        lblInstrucao.setBounds(140, 105, 1000, 35);
        add(lblInstrucao);

        lblAviso = new JLabel("", SwingConstants.CENTER);
        lblAviso.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
        lblAviso.setForeground(Color.RED);
        lblAviso.setBounds(140, 145, 1000, 30);
        add(lblAviso);

        // Campo da Palavra Secreta
        JLabel lblCampoPalavra = new JLabel("PALAVRA SECRETA:");
        lblCampoPalavra.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblCampoPalavra.setForeground(roxo);
        lblCampoPalavra.setBounds(340, 220, 240, 40);
        add(lblCampoPalavra);

        txtPalavraSecreta = new JPasswordField();
        txtPalavraSecreta.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        txtPalavraSecreta.setBounds(590, 220, 320, 40);
        add(txtPalavraSecreta);

        chkMostrarPalavra = new JCheckBox("Mostrar palavra digitada");
        chkMostrarPalavra.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        chkMostrarPalavra.setOpaque(false);
        chkMostrarPalavra.setBounds(590, 265, 250, 30);
        chkMostrarPalavra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chkMostrarPalavra.isSelected()) {
                    txtPalavraSecreta.setEchoChar((char) 0);
                } else {
                    txtPalavraSecreta.setEchoChar('•');
                }
            }
        });
        add(chkMostrarPalavra);

        // Campo da Dica
        JLabel lblCampoDica = new JLabel("DICA DA PALAVRA:");
        lblCampoDica.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblCampoDica.setForeground(roxo);
        lblCampoDica.setBounds(340, 320, 240, 40);
        add(lblCampoDica);

        txtDica = new JTextField();
        txtDica.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        txtDica.setBounds(590, 320, 320, 40);
        add(txtDica);

        btConfirmar = new JButton("Confirmar Palavra");
        btConfirmar.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        btConfirmar.setBounds(540, 400, 200, 50);
        btConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarConfirmacao();
            }
        });
        add(btConfirmar);

        JButton btVoltar = new JButton("Voltar");
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

    private void atualizarTelaParaJogadorAtual() {
        txtPalavraSecreta.setText("");
        txtDica.setText("");
        chkMostrarPalavra.setSelected(false);
        txtPalavraSecreta.setEchoChar('•');

        if (vezDoJogador1) {
            lblTitulo.setText("VEZ DE " + jogador1.getLogin().toUpperCase() + " ESCOLHER");
            lblInstrucao.setText("Digite a palavra secreta e a dica para " + jogador2.getLogin() + " descobrir:");
            lblAviso.setText("Atenção: " + jogador2.getLogin() + ", não olhe para a tela agora!");
            btConfirmar.setText("Confirmar e Passar");
        } else {
            lblTitulo.setText("VEZ DE " + jogador2.getLogin().toUpperCase() + " ESCOLHER");
            lblInstrucao.setText("Digite a palavra secreta e a dica para " + jogador1.getLogin() + " descobrir:");
            lblAviso.setText("Atenção: " + jogador1.getLogin() + ", não olhe para a tela agora!");
            btConfirmar.setText("Iniciar Duelo!");
        }
    }

    private void processarConfirmacao() {
        try {
            String palavraDigitada = new String(txtPalavraSecreta.getPassword()).trim();
            String dicaDigitada = txtDica.getText().trim();

            if (palavraDigitada.isEmpty() || dicaDigitada.isEmpty()) {
                throw new CamposVaziosException();
            }

            if (!palavraDigitada.matches("[a-zA-ZÀ-ÿ]+")) {
                JOptionPane.showMessageDialog(null,
                        "Digite apenas uma palavra contendo letras (sem espaços ou números)!",
                        "Palavra Inválida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (palavraDigitada.length() < 3) {
                JOptionPane.showMessageDialog(null,
                        "A palavra precisa ter pelo menos 3 letras!",
                        "Palavra Muito Curta",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Instanciando com o construtor exato: (palavra, dica, dificuldade)
            Palavra palavraCriada = new Palavra(palavraDigitada.toUpperCase(), dicaDigitada, dificuldade);

            if (vezDoJogador1) {
                palavraDoJ1 = palavraCriada;
                vezDoJogador1 = false;
                atualizarTelaParaJogadorAtual();
            } else {
                palavraDoJ2 = palavraCriada;
                dispose();
                new JanelaJogoCompetitivoEspecial(jogador1, jogador2, dificuldade, palavraDoJ1, palavraDoJ2);
            }

        } catch (CamposVaziosException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
}