import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipalJogoADM extends JanelaPrincipalJogo {
    private Jogador jogador;

    public JanelaPrincipalJogoADM(Jogador j) {
        super(j);
        GerarRelatorio();
    }

    private void GerarRelatorio() {
        //Botão de Teste Klebson, pode apagar ele ou substituir por outro botão! :)
        JButton btGerarRelatorio = new JButton("Gerar Relatório");
        btGerarRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CentralDeInformacoes centro = new CentralDeInformacoes();

                Mensageiro.enviarMensagem(jogador.getEmail(),"Relatorio: ", GeradorDeRelatorios.gerarRelatorio(centro));
            }
        });
        btGerarRelatorio.setBounds(430, 500, 140, 45);
        add(btGerarRelatorio);

    }
}