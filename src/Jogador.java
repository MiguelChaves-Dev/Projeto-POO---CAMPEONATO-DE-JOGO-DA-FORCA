import javax.swing.*;
import java.util.ArrayList;

public class Jogador {
    private String login;
    private String senha;
    private String email;
    private boolean isADM;
    private boolean isAtivo = true;
    private ArrayList<Palavra> palavrasJogadas = new ArrayList<Palavra>();
    private String caminhoIconeJogador = "/Resources/Logo 64.png";
    private int pontuacaoSolo;
    private int pontuacaoComp;

    public Jogador(String no, String em, String se, Boolean adm) {
        login = no;
        senha = se;
        email = em;
        isADM = adm;
    }

    //Soma a pontuação da partida Solo a pontuação ja salva do jogador
    public void somarPontuacaoSolo(int pont){
        pontuacaoSolo += pont;
    }

    //Soma a pontuação da partida Competitiva a pontuação ja salva do jogador
    public void somarPontuacaoComp(int pont){
        pontuacaoComp += pont;
    }

    //Retorna um booleano, se o jogador for ADM(true) ou não(false)
    public Boolean getIsADM(){
        return isADM;
    }

    public Boolean getIsAtivo(){
        return isAtivo;
    }

    //Retorna o icone do jogador
    public ImageIcon getIconeJogador(){
        if(caminhoIconeJogador != null){
            java.net.URL imgURL = getClass().getResource(caminhoIconeJogador);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            }
        }
        return null;
    }

    //Retorna o login do jogador
    public String getLogin() {
        return login;
    }

    //Retorna a senha do jogador
    public String getSenha(){
        return senha;
    }

    //Retorna o email do jogador
    public String getEmail() {
        return email;
    }

    //Retorna um arraylist que representa as palavras ja jogadas pelo jogador
    public ArrayList<Palavra> getPalavrasJogadas(){
        return palavrasJogadas;
    }

    //Retorna a pontuação do jogador no modo Solo
    public int getPontuacaoSolo(){
        return pontuacaoSolo;
    }

    //Retorna a pontuação do jogador no modo Competitivo
    public int getPontuacaoComp() {
        return pontuacaoComp;
    }

    public void setCaminhoIconeJogador(String novoCaminho){
        caminhoIconeJogador = novoCaminho;
    }

    //Adiciona as palavras ja jogadas ao arraylist de palavrasJogadas
    public void adicionarPalavraJogada(Palavra p){
        palavrasJogadas.add(p);
    }

    //Define uma nova senha para o jogador, recurso utilizado somente na parte de recuperação de senha
    public void setSenha(String se){
        senha = se;
    }

    public void setIsAtivo(Boolean is){
        isAtivo = is;
    }

    public String toString() {
        return login;
    }
}
