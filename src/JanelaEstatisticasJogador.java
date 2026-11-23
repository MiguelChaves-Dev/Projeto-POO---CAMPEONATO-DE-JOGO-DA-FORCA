import Exceptions.CamposVaziosException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class JanelaEstatisticasJogador extends LayoutJanela{
    private Jogador jogador;
    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();
    private JTable tabelaPartidas;
    private DefaultTableModel modeloTabela;
    public JanelaEstatisticasJogador(Jogador j){
        super("Campeonato de Forca - Minhas Estatísticas");
        try{
            Jogador jogadorAtual = central.recuperarJogadorPorEmail(j.getEmail());
            if(jogadorAtual != null){
                jogador = jogadorAtual;
            } else {
                jogador = j;
            }
        } catch(CamposVaziosException ex){
            //Esse catch não vale de nada, o usuário já confirmou o cadastro anteriormente :D
        }
        criarTexto();
        criarBotao();
        criarTabela();
        setVisible(true);
    }

    public void criarTexto(){
        Color roxo = new Color(110,70,200);

        JLabel lblTitulo = new JLabel("ESTATÍSTICAS DE " + jogador.getLogin().toUpperCase(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD,42));
        lblTitulo.setForeground(roxo);
        lblTitulo.setBounds(140,25,1000,55);
        add(lblTitulo);

        JPanel painelSolo = new JPanel();
        painelSolo.setLayout(null);
        painelSolo.setBackground(Color.WHITE);
        painelSolo.setBorder(BorderFactory.createLineBorder(roxo,3));
        painelSolo.setBounds(220,95,380,105);
        add(painelSolo);

        JLabel lblTituloSolo = new JLabel("PONTUAÇÃO MODO SOLO", SwingConstants.CENTER);
        lblTituloSolo.setFont(new Font("Comic Sans MS", Font.BOLD,18));
        lblTituloSolo.setForeground(Color.DARK_GRAY);
        lblTituloSolo.setBounds(0,10,380,25);
        painelSolo.add(lblTituloSolo);

        JLabel lblPontosSolo = new JLabel(jogador.getPontuacaoSolo() + " pts",SwingConstants.CENTER);
        lblPontosSolo.setFont((new Font("Comic Sans MS", Font.BOLD,40)));
        lblPontosSolo.setForeground(roxo);
        lblPontosSolo.setBounds(0,40,380,50);
        painelSolo.add(lblPontosSolo);

        JPanel painelComp = new JPanel();
        painelComp.setLayout(null);
        painelComp.setBackground(new Color(235, 250, 240));
        painelComp.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 70), 3));
        painelComp.setBounds(680, 95, 380, 105);
        add(painelComp);

        JLabel lblTituloComp = new JLabel("RANQUE COMPETITIVO", SwingConstants.CENTER);
        lblTituloComp.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTituloComp.setForeground(Color.DARK_GRAY);
        lblTituloComp.setBounds(0, 10, 380, 25);
        painelComp.add(lblTituloComp);

        JLabel lblPontosComp = new JLabel(jogador.getPontuacaoComp() + " pts", SwingConstants.CENTER);
        lblPontosComp.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        lblPontosComp.setForeground(new Color(0, 140, 70));
        lblPontosComp.setBounds(0, 40, 380, 50);
        painelComp.add(lblPontosComp);

        JLabel lblHistorico = new JLabel("Histórico das Minhas Partidas:");
        lblHistorico.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        lblHistorico.setForeground(roxo);
        lblHistorico.setBounds(80, 220, 500, 35);
        add(lblHistorico);
    }

    private void criarTabela(){
        String[] colunas = {"Data/Hora","Modo","Jogadores","Palavra(s)","Dificuldade","Resultado","Sua Pontuação"};

        modeloTabela = new DefaultTableModel(colunas, 0){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Partida p : central.getPartidas()){
            boolean participou = false;
            for(Jogador j : p.getJogadores()){
                if(j.getLogin().equals(jogador.getLogin())){
                    participou = true;
                    break;
                }
            }

            if (participou){
                String dataFormatada;
                if(p.getDataHora() != null){
                    dataFormatada = p.getDataHora().format(formatador);
                } else {
                    dataFormatada = "-";
                }

                String modo;
                if(p.isCompetitiva()){
                    modo = "Competitivo";
                } else{
                    modo = "Solo";
                }

                String nomesJogadores = "";
                for(int i = 0; i < p.getJogadores().size();i++){
                    nomesJogadores += p.getJogadores().get(i).getLogin();
                    if (i<p.getJogadores().size() - 1){
                        nomesJogadores += " vs ";
                    }
                }

                String palavrasUsadas = "";
                for(int i = 0; i < p.getPalavras().size();i++){
                    palavrasUsadas += p.getPalavras().get(i).getPalavra();
                    if(i<p.getPalavras().size() - 1){
                        palavrasUsadas += " / ";
                    }
                }

                int pontosObtidos = p.getPontosJogador1();
                if (p.isCompetitiva() && p.getJogadores().size() > 1){
                    if(p.getJogadores().get(1).getLogin().equals(jogador.getLogin())){
                        pontosObtidos = p.getPontosJogador2();
                    }
                }

                Object[] linha = {dataFormatada, modo, nomesJogadores, palavrasUsadas, p.getDificuldade(), p.getResultado(), pontosObtidos + " pts"};
                modeloTabela.addRow(linha);
            }
        }

        tabelaPartidas = new JTable(modeloTabela);
        tabelaPartidas.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        tabelaPartidas.setRowHeight(30);
        tabelaPartidas.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        tabelaPartidas.getTableHeader().setBackground(new Color(110, 70, 200));
        tabelaPartidas.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i < tabelaPartidas.getColumnCount(); i++){
            tabelaPartidas.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        JScrollPane scroll = new JScrollPane(tabelaPartidas);
        scroll.setBounds(80,260,1120,370);
        add(scroll);
    }

    private void criarBotao(){
        JButton btTrocar = new JButton("Trocar Ícone");
        btTrocar.setBounds(870,215,160,38);
        btTrocar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btTrocar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MiniJanelaTrocarIcone(jogador);
            }
        });
        add(btTrocar);

        JButton btRanque = new JButton("Ver Ranques");
        btRanque.setBounds(1040,215,160,38);
        btRanque.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btRanque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaRanque(jogador);
            }
        });
        add(btRanque);

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
