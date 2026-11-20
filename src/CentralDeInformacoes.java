import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;
import Exceptions.PalavrasIndisponiveisException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CentralDeInformacoes {
    ArrayList<Jogador> jogadores = new ArrayList<Jogador> ();
    ArrayList<Palavra> palavras = new ArrayList<Palavra>();
    ArrayList<Partida> partidas = new ArrayList<Partida>();


    //Adiciona um objeto do tipo Partida na Central de Informações podendo salva-la
    public boolean adicionarPartida(Partida p){
        for(int i = 0; i < partidas.size(); i++){
            if(partidas.get(i).equals(p)){
                return false;
            }
        }
        partidas.add(p);
        return true;
    }

    //Retorna as partidas salvas na central
    public ArrayList<Partida> getPartidas(){
        return partidas;
    }

    //Retorna um arraylist com todos os jogadores cadastrados
    public ArrayList<Jogador> getTodosJogadores() {
        return jogadores;
    }

    //Adiciona um objeto do tipo Jogador na Central de Informações podendo salva-lo
    public boolean adicionarJogador(Jogador j) {
        for(int i = 0; i < jogadores.size();i++) {
            if (jogadores.get(i).getEmail().equalsIgnoreCase(j.getEmail()) || jogadores.get(i).getLogin().equalsIgnoreCase(j.getLogin())) {
                return false;
            }
        }
        jogadores.add(j);
        return true;
    }

    //Método utilizado na função de recuperação de senha, basicamente troca a senha do jogador selecionado
    public void trocarSenhaJogador(Jogador j, String senha) throws CamposVaziosException {
        recuperarJogadorPorEmail(j.getEmail()).setSenha(senha);
    }

    //Retorna um Jogador salvo na Central caso o login e senha sejam iguais
    public Jogador recuperarJogadorPorLogin(String login, String senha) throws ContaNaoEncontradaException, CamposVaziosException {
        if(login.isBlank() ||  senha.isBlank()){
            throw new CamposVaziosException();
        }
        for(int i = 0; i < jogadores.size(); i++){
            if(jogadores.get(i).getLogin().equalsIgnoreCase(login) && jogadores.get(i).getSenha().equals(senha)){
                return jogadores.get(i);
            }
        }
        throw new ContaNaoEncontradaException();
    }

    //Retorna um Jogador salvo na Central caso o email seja igual
    public Jogador recuperarJogadorPorEmail(String email) throws ContaNaoEncontradaException, CamposVaziosException {
        if(email.isBlank()){
            throw new CamposVaziosException();
        }
        for (int i = 0; i< jogadores.size(); i++){
            if (jogadores.get(i).getEmail().equalsIgnoreCase(email)) {
                return jogadores.get(i);
            }
        }
        throw new ContaNaoEncontradaException();
    }

    //Retorna um arraylist de palavras que estão salvas na central
    public ArrayList<Palavra> getPalavras() {
        return palavras;
    }

    public boolean adicionarPalavra(Palavra p){
        for(int i = 0; i < palavras.size();i++){
            if(palavras.get(i).getPalavra().equals(p.getPalavra())){
                return false;
            }
        }
        palavras.add(p);
        return true;
    }

    //Sorteia uma das palavras cadastradas no arraylist de palavras para o jogo, caso o jogador ja tenha jogado essa palavra ele lança um erro para não dar continuidade a partida
    public Palavra sortearPalavra(Jogador j, Dificuldade d) throws PalavrasIndisponiveisException, CamposVaziosException {
        Jogador jogadorAtual = recuperarJogadorPorEmail(j.getEmail());
        if (jogadorAtual == null) {
            jogadorAtual = j;
        }

        ArrayList<Palavra> disponiveis = new ArrayList<>();

        for (int i = 0; i < palavras.size(); i++) {
            Palavra p = palavras.get(i);

            if (p.getDificuldade() == d && !jogadorAtual.getPalavrasJogadas().contains(p)) {
                disponiveis.add(p);
            }
        }

        if (disponiveis.isEmpty()) {
            throw new PalavrasIndisponiveisException();
        }

        Random random = new Random();
        int indiceSorteado = random.nextInt(disponiveis.size());
        return disponiveis.get(indiceSorteado);
    }

    public ArrayList<Palavra> sortearPalavrasCompetitivo(Jogador j1, Jogador j2, Dificuldade d) throws PalavrasIndisponiveisException, CamposVaziosException {
        Jogador jog1 = recuperarJogadorPorEmail(j1.getEmail());
        Jogador jog2 = recuperarJogadorPorEmail(j2.getEmail());

        // 1. Filtra todas as palavras daquela dificuldade que NENHUM dos dois jogou
        ArrayList<Palavra> candidatas = new ArrayList<>();
        for (Palavra p : palavras) {
            if (p.getDificuldade() == d
                    && !jog1.getPalavrasJogadas().contains(p)
                    && !jog2.getPalavrasJogadas().contains(p)) {
                candidatas.add(p);
            }
        }

        // 2. Procura duas palavras diferentes que tenham exatamente o mesmo número de letras
        Collections.shuffle(candidatas); // Embaralha a lista
        for (int i = 0; i < candidatas.size(); i++) {
            for (int k = i + 1; k < candidatas.size(); k++) {
                Palavra p1 = candidatas.get(i);
                Palavra p2 = candidatas.get(k);

                if (p1.getPalavra().length() == p2.getPalavra().length()) {
                    ArrayList<Palavra> parSorteado = new ArrayList<>();
                    parSorteado.add(p1);
                    parSorteado.add(p2);
                    return parSorteado;
                }
            }
        }

        // Se não achou nenhum par com o mesmo tamanho, lança a exceção
        throw new PalavrasIndisponiveisException();
    }

    //Retorna uma Palavra(Classe) caso a palavra(String) seja igual
    public Palavra recuperarPalavra(String palavra){
        for (int i = 0;  i< palavras.size(); i++){
            if (palavras.get(i).getPalavra().equalsIgnoreCase(palavra)){
                return palavras.get(i);
            }
        }
        return null;
    }

}
