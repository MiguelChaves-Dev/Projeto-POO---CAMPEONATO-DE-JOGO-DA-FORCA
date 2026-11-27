import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        Persistencia persistencia = new Persistencia();
        CentralDeInformacoes central = persistencia.recuperarCentral();
        boolean existsADM = false;
        ArrayList<Jogador> jogadores = central.getTodosJogadores();
        for(int i = 0; i<jogadores.size(); i++){
            if (jogadores.get(i).getIsADM() == true){
                existsADM = true;
            }
        }

        if(existsADM){
            new JanelaLogin();
        } else{
            new JanelaCadastroADM();
        }
    }
}