import java.time.LocalDateTime;
import java.util.ArrayList;

public class Partida {
    private LocalDateTime dataHora;
    private boolean competitiva;
    private Dificuldade dificuldade;
    private ArrayList<Palavra> palavras = new ArrayList<>();
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private ArrayList<HistoricoPalpites> logsOperacoes = new ArrayList<>();
    private int pontosJogador1;
    private int pontosJogador2;
    private String resultado;

    public Partida() {
        this.dataHora = LocalDateTime.now();
    }

    public Partida(Jogador jogador, Palavra palavra, Dificuldade dificuldade,
                   ArrayList<HistoricoPalpites> logs, int pontos, String resultado) {
        this.dataHora = LocalDateTime.now();
        this.competitiva = false;
        this.dificuldade = dificuldade;
        this.jogadores.add(jogador);
        this.palavras.add(palavra);
        this.logsOperacoes = logs;
        this.pontosJogador1 = pontos;
        this.resultado = resultado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isCompetitiva() {
        return competitiva;
    }

    public void setCompetitiva(boolean competitiva) {
        this.competitiva = competitiva;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public ArrayList<Palavra> getPalavras() {
        return palavras;
    }

    public void setPalavras(ArrayList<Palavra> palavras) {
        this.palavras = palavras;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public ArrayList<HistoricoPalpites> getLogsOperacoes() {
        return logsOperacoes;
    }

    public void setLogsOperacoes(ArrayList<HistoricoPalpites> logsOperacoes) {
        this.logsOperacoes = logsOperacoes;
    }

    public int getPontosJogador1() {
        return pontosJogador1;
    }

    public void setPontosJogador1(int pontosJogador1) {
        this.pontosJogador1 = pontosJogador1;
    }

    public int getPontosJogador2() {
        return pontosJogador2;
    }

    public void setPontosJogador2(int pontosJogador2) {
        this.pontosJogador2 = pontosJogador2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
