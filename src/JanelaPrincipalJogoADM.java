import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JanelaPrincipalJogoADM extends JanelaPrincipalJogo {

    public JanelaPrincipalJogoADM(Jogador j) {
        super(j);
        criarBotoes(j);
    }
    public void criarBotoes(Jogador j){
        JButton btGerarRelatorio = new JButton("Gerar Relatório");
        btGerarRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File pdf = new File("relatorio.pdf"); // CRIOU O ARQUIVO DO RELATORIO
                if (!pdf.exists()) { // verifica se o relatorio existe
                    JOptionPane.showMessageDialog(null, "PDF não encontrado em: " + pdf.getAbsolutePath());
                    return;
                }
                Mensageiro.enviarComAnexo(j.getEmail(), "Relatório", "Segue seu relatório em anexo.", pdf);
            }
        });
        btGerarRelatorio.setBounds(430, 500, 200, 45);
        add(btGerarRelatorio);
        JButton btImportar = new JButton("Importar Palavras");
        Persistencia persistencia = new Persistencia();   // grava no central.xml
        CentralDeInformacoes central = persistencia.recuperarCentral();
        btImportar.setBounds(430, 560, 160, 45);
        btImportar.addActionListener(e -> {
            JFileChooser seletor = new JFileChooser();
            if (seletor.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            try {
                String relatorio = ExtratorPalavrasCSV.importar(
                        seletor.getSelectedFile().getAbsolutePath(), central);
                persistencia.salvarCentral(central);
                JTextArea area = new JTextArea(relatorio, 15, 50);
                area.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(area),
                        "Relatório da importação", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível ler o arquivo.");
            }
        });
        add(btImportar);
    }
}

