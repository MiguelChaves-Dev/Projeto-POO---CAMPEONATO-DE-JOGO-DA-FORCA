import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JanelaPrincipalJogoADM extends JanelaPrincipalJogo {

    public JanelaPrincipalJogoADM(Jogador j) {
        super(j);

        JButton btGerarRelatorio = new JButton("Gerar Relatório");
        btGerarRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File pdf = new File("relatorio.pdf"); // ta dando erro aqui, preciso corrigir dps
                if (!pdf.exists()) {
                    JOptionPane.showMessageDialog(null, "PDF não encontrado em: " + pdf.getAbsolutePath());
                    return;
                }
                Mensageiro.enviarComAnexo(j.getEmail(), "Relatório", "Segue seu relatório em anexo.", pdf);
            }
        });
        btGerarRelatorio.setBounds(430, 500, 200, 45);
        add(btGerarRelatorio);

    }
}

