import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;
import Exceptions.PalavrasIndisponiveisException;
import Exceptions.SenhaIncorretaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JanelaPrepararCompetitivo extends LayoutJanela{
    private Jogador jogador1;
    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();

    private JComboBox<String> cbAdversarios;
    private JPasswordField txtSenhaAdversario;
    private JComboBox<Dificuldade> cbDificuldade;

    public JanelaPrepararCompetitivo(Jogador j1) {
        super("Campeonato de Forca");
        this.jogador1 = j1;

        criarTexto();
        criarCampos();
        criarBotao();
        setVisible(true);
    }

    private void criarTexto() {
        Color roxo = new Color(110, 70, 200);

        JLabel lblTitulo = new JLabel("MODO COMPETITIVO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 56));
        lblTitulo.setForeground(roxo);
        lblTitulo.setBounds(240, 50, 800, 70);
        add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Selecione o adversário e confirme a senha dele", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        lblSubtitulo.setBounds(240, 125, 800, 35);
        add(lblSubtitulo);

        JLabel lblAdversario = new JLabel("ADVERSÁRIO:");
        lblAdversario.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblAdversario.setForeground(roxo);
        lblAdversario.setBounds(380, 220, 200, 36);
        add(lblAdversario);

        JLabel lblSenha = new JLabel("SENHA DELE:");
        lblSenha.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblSenha.setForeground(roxo);
        lblSenha.setBounds(380, 290, 200, 36);
        add(lblSenha);

        JLabel lblDificuldade = new JLabel("DIFICULDADE:");
        lblDificuldade.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblDificuldade.setForeground(roxo);
        lblDificuldade.setBounds(380, 360, 200, 36);
        add(lblDificuldade);
    }

    private void criarCampos() {
        // 1. Preenche o ComboBox apenas com jogadores ATIVOS e diferentes do jogador1
        cbAdversarios = new JComboBox<>();
        cbAdversarios.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        cbAdversarios.setBounds(580, 220, 320, 36);

        for (Jogador j : central.getTodosJogadores()) {
            // Ignora o próprio jogador logado e contas inativadas pelo admin
            if (j.getIsAtivo() && !j.getLogin().equalsIgnoreCase(jogador1.getLogin())) {
                cbAdversarios.addItem(j.getLogin());
            }
        }
        add(cbAdversarios);

        // 2. Campo oculto para a senha do adversário
        txtSenhaAdversario = new JPasswordField();
        txtSenhaAdversario.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        txtSenhaAdversario.setBounds(580, 290, 320, 36);
        add(txtSenhaAdversario);

        // 3. Seleção da dificuldade do duelo
        cbDificuldade = new JComboBox<>(Dificuldade.values());
        cbDificuldade.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        cbDificuldade.setBounds(580, 360, 320, 36);
        add(cbDificuldade);
    }

    private void criarBotao() {
        JButton btIniciar = new JButton("Iniciar Duelo");
        btIniciar.setBounds(540, 460, 200, 50);
        btIniciar.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        btIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Verifica se existe algum adversário disponível na lista
                    if (cbAdversarios.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, "Não há outros jogadores ativos cadastrados!", "Atenção", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String loginAdversario = cbAdversarios.getSelectedItem().toString();
                    String senhaDigitada = new String(txtSenhaAdversario.getPassword());

                    if (senhaDigitada.isEmpty()) {
                        throw new CamposVaziosException();
                    }

                    // Busca o objeto Jogador do adversário e valida a senha
                    Jogador jogador2 = central.recuperarJogadorPorLogin(loginAdversario,senhaDigitada);

                    if (jogador2 == null || !jogador2.getSenha().equals(senhaDigitada)) {
                        throw new SenhaIncorretaException();
                    }

                    // Tenta sortear as 2 palavras antes de fechar a tela
                    Dificuldade difEscolhida = (Dificuldade) cbDificuldade.getSelectedItem();
                    ArrayList<Palavra> palavrasDuelo = central.sortearPalavrasCompetitivo(jogador1, jogador2, difEscolhida);

                    // Tudo certo! Abre a janela do jogo competitivo
                    dispose();
                    new JanelaJogoCompetitivo(jogador1, jogador2, difEscolhida, palavrasDuelo);

                } catch (CamposVaziosException | PalavrasIndisponiveisException | SenhaIncorretaException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btIniciar);

        JButton btVoltar = new JButton("Voltar");
        btVoltar.setBounds(10, 660, 80, 50);
        btVoltar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaModoDeJogo(jogador1);
            }
        });
        add(btVoltar);
    }
}
