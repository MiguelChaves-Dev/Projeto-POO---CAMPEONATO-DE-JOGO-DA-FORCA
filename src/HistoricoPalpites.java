public class HistoricoPalpites {
    private char letra;
    private boolean acertou;
    private String loginJogador;

    public HistoricoPalpites(char letra, boolean acertou, String loginJogador) {
        this.letra = letra;
        this.acertou = acertou;
        this.loginJogador = loginJogador;
    }

    //Retorna a letra do palpite
    public char getLetra() {
        return letra;
    }

    //Retorna um booleano que diz se o jogador acertou(true) ou errou(false) o palpite
    public boolean isAcertou() {
        return acertou;
    }

    //Retorna a string que representa o jogador que fez o palpite
    public String getLoginJogador() {
        return loginJogador;
    }

    public String toString() {
        if(acertou){
            return loginJogador + " chutou '" + letra + "' ( Acertou )";
        } else{
            return loginJogador + "chutou '" + letra + "' ( Errou )";
        }
    }
}
