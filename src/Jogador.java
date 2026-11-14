public class Jogador {
    private String login;
    private String senha;
    private String email;
    private Boolean isADM;
    private int qntVitoria;
    private int qntDerrota;
    private int pontuacao;

    public Jogador(String no, String em, String se, Boolean adm) {
        login = no;
        senha = se;
        email = em;
        isADM = adm;
    }

    public void somarPontuacao(boolean ganhou, int pont){
        if(ganhou){
            qntVitoria++;
        } else {
            qntDerrota++;
        }
        pontuacao += pont;
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
    public void setSenha(String se){
        senha = se;
    }
    public String getEmail() {
        return email;
    }
}
