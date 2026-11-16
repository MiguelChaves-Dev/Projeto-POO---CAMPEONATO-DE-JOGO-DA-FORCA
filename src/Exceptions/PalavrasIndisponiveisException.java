package Exceptions;

public class PalavrasIndisponiveisException extends RuntimeException {
    public PalavrasIndisponiveisException() {
        super("No momento não há palavras disponíveis nessa Dificuldade para jogar!");
    }
}
