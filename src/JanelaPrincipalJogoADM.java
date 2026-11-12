import javax.swing.*;

public class JanelaPrincipalJogoADM extends JanelaPrincipalJogo{
    private Jogador jogador;
    public JanelaPrincipalJogoADM(Jogador j){
        super(j);
        criarBotao();
    }
    private void criarBotao(){
        //Botão de Teste Klebson, pode apagar ele ou substituir por outro botão! :)
        JButton btTeste = new JButton("Teste");
        btTeste.setBounds(430,500,100,45);
        add(btTeste);
    }
}
