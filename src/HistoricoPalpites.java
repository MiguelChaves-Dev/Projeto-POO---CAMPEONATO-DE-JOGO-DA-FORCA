public class HistoricoPalpites {
    private char letra;
    private boolean acertou;
    private String loginJogador;

    public HistoricoPalpites(char letra, boolean acertou, String loginJogador) {
        this.letra = letra;
        this.acertou = acertou;
        this.loginJogador = loginJogador;
    }

    public char getLetra() {
        return letra;
    }

    public boolean isAcertou() {
        return acertou;
    }

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
