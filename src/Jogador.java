import java.util.ArrayList;

public class Jogador {
    private String login;
    private String senha;
    private String email;
    private Boolean isADM;
    private ArrayList<Palavra> palavrasJogadas = new ArrayList<>();
    private int qntVitoria;
    private int qntDerrota;
    private int pontuacaoSolo;

    public Jogador(String no, String em, String se, Boolean adm) {
        login = no;
        senha = se;
        email = em;
        isADM = adm;
    }

    public void somarPontuacaoSolo(int pont){
        pontuacaoSolo += pont;
    }

    public Boolean getIsADM(){
        return isADM;
    }
    public String toString() {
        return login;
    }

    public String getLogin() {
        return login;
    }
    public String getSenha(){
        return senha;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<Palavra> getPalavrasJogadas(){
        return palavrasJogadas;
    }
    public void adicionarPalavraJogada(Palavra p){
        palavrasJogadas.add(p);
    }
    public void setSenha(String se){
        senha = se;
    }
}
