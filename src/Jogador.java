public class Jogador {
    private String login;
    private String senha;
    private String email;
    private Grau grau;

    public Jogador(String no, String em, String se, Grau gr) {
        login = no;
        senha = se;
        email = em;
        grau = gr;
    }
    public Grau getGrau(){
        return grau;
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
