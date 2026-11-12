import Exceptions.CamposVaziosException;
import Exceptions.ContaNaoEncontradaException;

import java.util.ArrayList;
public class CentralDeInformacoes {
    ArrayList<Jogador> jogadores = new ArrayList<Jogador> ();
    ArrayList<Palavra> palavras = new ArrayList<Palavra>();

    public ArrayList<Jogador> getTodosJogadores() {
        return jogadores;
    }

    public void setTodosJogadores(ArrayList<Jogador> j) {
        jogadores = j;
    }

    public boolean adicionarJogador(Jogador j) {
        for(int i = 0; i < jogadores.size();i++) {
            if (jogadores.get(i).getEmail().equals(j.getEmail()) || jogadores.get(i).getLogin().equals(j.getLogin())) {
                return false;
            }
        }
        jogadores.add(j);
        return true;
    }

    public void trocarSenhaJogador(Jogador j, String senha) throws CamposVaziosException {
        recuperarJogadorPorEmail(j.getEmail()).setSenha(senha);
    }

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

    public Palavra recuperarPalavra(String palavra){
        for (int i = 0;  i< palavras.size(); i++){
            if (palavras.get(i).getPalavra().equals(palavra)){
                return palavras.get(i);
            }
        }
        return null;
    }

}
