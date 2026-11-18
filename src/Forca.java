import Exceptions.CamposVaziosException;

import java.util.ArrayList;
import java.util.List;

public class Forca {
    private Palavra palavra;
    private String palavraSecreta;
    private char[] palavraOculta;
    private int erros = 0;
    private List<Character> letrasTentadas = new ArrayList<>();
    private ArrayList<HistoricoPalpites> logsDaRodada = new ArrayList<HistoricoPalpites>();

    public Forca(Palavra p){
        palavra = p;
        palavraSecreta = p.getPalavra().toUpperCase();
        palavraOculta = new char[p.getPalavra().length()];
        for(int i = 0; i < palavraOculta.length; i++){
            palavraOculta[i] = '_';
        }
    }

    //Verifica e altera a palavraOculta caso o jogador tenha acertado um palpite
    public boolean tentarLetra(char letra, Jogador jogadorDaVez) throws CamposVaziosException {
        if(String.valueOf(letra).isBlank()){
            throw new CamposVaziosException();
        }
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

    //Verifica se a palavraOculta ja foi descoberta
    public boolean isVenceu(){
        for(char c : palavraOculta){
            if(c == '_'){
                return false;
            }
        }
        return true;
    }

    //Verifica se o jogador ja ultrapassou o limite de erros
    public boolean isPerdeu(){
        return erros >= 6;
    }

    //Calcula a pontuação do jogador dependendo do seu desempenho na partida
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

    //Retorna a palavra sorteada na criação da Forca
    public Palavra getPalavra(){
        return palavra;
    }

    //Retorna a palavra sorteada mas de forma oculta
    public String getPalavraOculta() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraOculta) {
            sb.append(c).append(' ');
        }
        return sb.toString().trim();
    }

    //Retorna a quantidade de erros que o jogador ja cometeu
    public int getErros() {
        return erros;
    }

    //Retorna uma lista com todos os palpites jogados
    public List<Character> getLetrasTentadas() {
        return letrasTentadas;
    }
    //Retorna um arraylist com todos os palpites jogados

    public ArrayList<HistoricoPalpites> getLogsDaRodada() {
        return logsDaRodada;
    }
}

