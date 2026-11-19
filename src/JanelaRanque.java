import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JanelaRanque extends LayoutJanela {
    private Jogador jogadorLogado;
    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();

    public JanelaRanque(Jogador j) {
        super("Campeonato de Forca - Ranques");
        this.jogadorLogado = j;

        criarTexto();
        criarTabelas();
        criarBotao();
        setVisible(true);
    }

    private void criarTexto() {
        Color roxo = new Color(110, 70, 200);

        JLabel lblTitulo = new JLabel("RANQUE DOS JOGADORES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        lblTitulo.setForeground(roxo);
        lblTitulo.setBounds(240, 25, 800, 60);
        add(lblTitulo);

        // Título Ranque Solo (Esquerda)
        JLabel lblSolo = new JLabel("RANQUE MODO SOLO", SwingConstants.CENTER);
        lblSolo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblSolo.setForeground(roxo);
        lblSolo.setBounds(80, 110, 520, 35);
        add(lblSolo);

        // Título Ranque Competitivo (Direita)
        JLabel lblComp = new JLabel("RANQUE COMPETITIVO", SwingConstants.CENTER);
        lblComp.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblComp.setForeground(new Color(0, 140, 70));
        lblComp.setBounds(680, 110, 520, 35);
        add(lblComp);
    }

    private void criarTabelas() {
        String[] colunas = {"Posição", "Jogador", "Pontuação"};

        // --- 1. TABELA RANQUE SOLO (Esquerda) ---
        DefaultTableModel modeloSolo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Copia a lista de jogadores e ordena do maior para o menor em pontosSolo
        ArrayList<Jogador> listaSolo = new ArrayList<>(central.getTodosJogadores());
        Collections.sort(listaSolo, new Comparator<Jogador>() {
            public int compare(Jogador j1, Jogador j2) {
                return Integer.compare(j2.getPontuacaoSolo(), j1.getPontuacaoSolo());
            }
        });

        for (int i = 0; i < listaSolo.size(); i++) {
            Jogador j = listaSolo.get(i);
            Object[] linha = {(i + 1) + "º", j.getLogin(), j.getPontuacaoSolo() + " pts"};
            modeloSolo.addRow(linha);
        }

        JTable tabelaSolo = configurarTabela(modeloSolo, new Color(110, 70, 200));
        JScrollPane scrollSolo = new JScrollPane(tabelaSolo);
        scrollSolo.setBounds(80, 155, 520, 470);
        add(scrollSolo);

        // --- 2. TABELA RANQUE COMPETITIVO (Direita) ---
        DefaultTableModel modeloComp = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Copia a lista de jogadores e ordena do maior para o menor em pontosCompetitivo
        ArrayList<Jogador> listaComp = new ArrayList<>(central.getTodosJogadores());
        Collections.sort(listaComp, new Comparator<Jogador>() {
            public int compare(Jogador j1, Jogador j2) {
                return Integer.compare(j2.getPontuacaoComp(), j1.getPontuacaoComp());
            }
        });

        for (int i = 0; i < listaComp.size(); i++) {
            Jogador j = listaComp.get(i);
            Object[] linha = {(i + 1) + "º", j.getLogin(), j.getPontuacaoComp() + " pts"};
            modeloComp.addRow(linha);
        }

        JTable tabelaComp = configurarTabela(modeloComp, new Color(0, 140, 70));
        JScrollPane scrollComp = new JScrollPane(tabelaComp);
        scrollComp.setBounds(680, 155, 520, 470);
        add(scrollComp);
    }

    private JTable configurarTabela(DefaultTableModel modelo, Color corCabecalho) {
        JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        tabela.setRowHeight(32);
        tabela.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        tabela.getTableHeader().setBackground(corCabecalho);
        tabela.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }
        return tabela;
    }

    private void criarBotao() {
        JButton btVoltar = new JButton("Voltar");
        btVoltar.setBounds(10, 660, 80, 50);
        btVoltar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaEstatisticasJogador(jogadorLogado);
            }
        });
        add(btVoltar);
    }
}