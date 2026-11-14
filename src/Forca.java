import java.util.ArrayList;
import java.util.List;

public class Forca {
    private Palavra palavra;
    private String palavraSecreta;
    private char[] palavraOculta;
    private int erros = 0;
    private List<Character> letrasTentadas = new ArrayList<>();
    private List<HistoricoPalpites> logsDaRodada = new ArrayList<>();

    public Forca(Palavra p){
        palavra = p;
        palavraSecreta = p.getPalavra().toUpperCase();
        palavraOculta = new char[p.getPalavra().length()];
        for(int i = 0; i < palavraOculta.length; i++){
            palavraOculta[i] = '_';
        }
    }

    public boolean tentarLetra(char letra, Jogador jogadorDaVez){
        letra = Character.toUpperCase(letra);

        if (letrasTentadas.contains(letra)){
            return false;
        }
        letrasTentadas.add(letra);

        boolean acertou = false;

        for (int i = 0; i < palavraSecreta.length(); i++){
            if (palavraSecreta.charAt(i) == letra){
                palavraOculta[i] = letra;
                acertou = true;
            }
        }
        if(!acertou){
            erros++;
        }
        logsDaRodada.add(new HistoricoPalpites(letra, acertou, jogadorDaVez.getLogin()));
        return acertou;
    }

    public boolean isVenceu(){
        for(char c : palavraOculta){
            if(c == '_'){
                return false;
            }
        }
        return true;
    }

    public boolean isPerdeu(){
        return erros >= 6;
    }

    public int calcularPontuacao(){
        if (isPerdeu()){
            return -100;
        }

        int tamanho = palavraSecreta.length();
        int pontosBase = 0;

        if(tamanho >= 3 && tamanho <= 4){
            pontosBase = 100;
        } else if(tamanho >= 5 && tamanho <= 6){
            pontosBase = 150;
        } else if(tamanho >= 7 && tamanho <= 8){
            pontosBase = 200;
        } else if(tamanho >= 9){
            pontosBase = 300;
        }

        Dificuldade dificuldade = palavra.getDificuldade();
        double multiplicador = 1.0;

        if (dificuldade == Dificuldade.FACIL){
            multiplicador = 1.0;
        } else if (dificuldade == Dificuldade.MEDIO){
            multiplicador = 1.5;
        } else if (dificuldade == Dificuldade.DIFICIL){
            multiplicador = 2.0;
        }

        int bonusEficiecia = 0;

        if(erros == 0){
            bonusEficiecia = 100;
        } else if (erros == 1){
            bonusEficiecia = 75;
        } else if (erros == 2){
            bonusEficiecia = 50;
        } else if (erros == 3){
            bonusEficiecia = 25;
        } else {
            bonusEficiecia = 0;
        }

        int pontuacaoFinal = (int) (pontosBase*multiplicador) + bonusEficiecia;
        return pontuacaoFinal;
    }

    public Palavra getPalavra(){
        return palavra;
    }

    public String getPalavraOculta() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraOculta) {
            sb.append(c).append(' ');
        }
        return sb.toString().trim();
    }

    public int getErros() {
        return erros;
    }

    public List<Character> getLetrasTentadas() {
        return letrasTentadas;
    }
    public List<HistoricoPalpites> getLogsDaRodada() {
        return logsDaRodada;
    }
}

