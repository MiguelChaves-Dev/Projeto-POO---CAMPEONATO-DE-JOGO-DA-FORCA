import Exceptions.CamposVaziosException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniJanelaTrocarIcone extends LayoutJanela {
    private Jogador jogador;
    private Persistencia persistencia = new Persistencia();
    private CentralDeInformacoes central = persistencia.recuperarCentral();
    public MiniJanelaTrocarIcone(Jogador j){
       super("Campeonato de Forca - Trocar Ícone", 600 , 300);
       try{
        jogador = central.recuperarJogadorPorEmail(j.getEmail());
       } catch (CamposVaziosException e) {
           //Não Acontece Nada
       }
       criarTexto();
       criarBotao();
       setVisible(true);
    }
    private void criarTexto(){
        Color roxo = new Color(110,70,200);
        JLabel lblTitulo = new JLabel("SELECIONE UM ÍCONE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD,35));
        lblTitulo.setBounds(0,10,600,80);
        lblTitulo.setForeground(roxo);
        add(lblTitulo);
    }
    private void criarBotao(){
        JButton btIconePadrao = new JButton(new ImageIcon(getClass().getResource("/Resources/Logo 64.png")));
        btIconePadrao.setBounds(40,110,80,80);
        btIconePadrao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ícone alterado com sucesso!");
                jogador.setCaminhoIconeJogador("/Resources/Logo 64.png");
                persistencia.salvarCentral(central);
            }
        });
        add(btIconePadrao);

        JButton btIconePalhaco = new JButton(new ImageIcon(getClass().getResource("/Resources/LogoPalhaço.png")));
        btIconePalhaco.setBounds(150,110,80,80);
        btIconePalhaco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jogador.getPontuacaoSolo() > 1000 || jogador.getPontuacaoComp() > 5){
                    JOptionPane.showMessageDialog(null, "Ícone alterado com sucesso!");
                    jogador.setCaminhoIconeJogador("/Resources/LogoPalhaço.png");
                    persistencia.salvarCentral(central);
                }else{
                    JOptionPane.showMessageDialog(null,"Você não tem pontos o suficiente para utilizar esse icone!\nPontos nescessários:\nSolo: 1000pts\nCompetitivo: 5pts", "Pontos insuficientes!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btIconePalhaco);

        JButton btIconeCoroa = new JButton(new ImageIcon(getClass().getResource("/Resources/LogoCoroa.png")));
        btIconeCoroa.setBounds(260,110,80,80);
        btIconeCoroa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jogador.getPontuacaoSolo() > 2500 || jogador.getPontuacaoComp() > 10){
                    JOptionPane.showMessageDialog(null, "Ícone alterado com sucesso!");
                    jogador.setCaminhoIconeJogador("/Resources/LogoCoroa.png");
                    persistencia.salvarCentral(central);
                } else {
                    JOptionPane.showMessageDialog(null,"Você não tem pontos o suficiente para utilizar esse icone!\nPontos nescessários:\nSolo: 2500pts\nCompetitivo: 10pts", "Pontos insuficientes!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btIconeCoroa);

        JButton btIconeBone = new JButton(new ImageIcon(getClass().getResource("/Resources/LogoBoneDT.png")));
        btIconeBone.setBounds(370,110,80,80);
        btIconeBone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jogador.getPontuacaoSolo() > 5000 || jogador.getPontuacaoComp() > 20){
                    JOptionPane.showMessageDialog(null, "Ícone alterado com sucesso!");
                    jogador.setCaminhoIconeJogador("/Resources/LogoBoneDT.png");
                    persistencia.salvarCentral(central);
                } else {
                    JOptionPane.showMessageDialog(null,"Você não tem pontos o suficiente para utilizar esse icone!\nPontos nescessários:\nSolo: 5000pts\nCompetitivo: 20pts", "Pontos insuficientes!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btIconeBone);

        JButton btIconeCleyton = new JButton(new ImageIcon(getClass().getResource("/Resources/LogoCleyton.png")));
        btIconeCleyton.setBounds(480,110,80,80);
        btIconeCleyton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jogador.getPontuacaoSolo() > 10000 || jogador.getPontuacaoComp() > 30){
                    JOptionPane.showMessageDialog(null, "Ícone alterado com sucesso!");
                    jogador.setCaminhoIconeJogador("/Resources/LogoCleyton.png");
                    persistencia.salvarCentral(central);
                } else{
                    JOptionPane.showMessageDialog(null,"Você não tem pontos o suficiente para utilizar esse icone!\nPontos nescessários:\nSolo: 10000pts\nCompetitivo: 30pts", "Pontos insuficientes!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btIconeCleyton);

        JButton btVoltar = new JButton("Voltar");
        btVoltar.setBounds(10,260,80,30);
        btVoltar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JanelaEstatisticasJogador(jogador);
            }
        });
        add(btVoltar);
    }

}
