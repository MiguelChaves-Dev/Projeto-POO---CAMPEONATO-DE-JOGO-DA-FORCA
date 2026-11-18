import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;
import Exceptions.PalavrasIndisponiveisException;

import javax.swing.*;
import java.util.ArrayList;
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

    //Retorna um arraylist com todos os jogadores cadastrados
    public ArrayList<Jogador> getTodosJogadores() {
        return jogadores;
    }

    //Adiciona um objeto do tipo Jogador na Central de Informações podendo salva-lo
    public boolean adicionarJogador(Jogador j) {
        for(int i = 0; i < jogadores.size();i++) {
            if (jogadores.get(i).getEmail().equals(j.getEmail()) || jogadores.get(i).getLogin().equals(j.getLogin())) {
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
            if(jogadores.get(i).getLogin().equals(login) && jogadores.get(i).getSenha().equals(senha)){
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
            if (jogadores.get(i).getEmail().equals(email)) {
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

    //Retorna uma Palavra(Classe) caso a palavra(String) seja igual
    public Palavra recuperarPalavra(String palavra){
        for (int i = 0;  i< palavras.size(); i++){
            if (palavras.get(i).getPalavra().equals(palavra)){
                return palavras.get(i);
            }
        }
        return null;
    }

}
