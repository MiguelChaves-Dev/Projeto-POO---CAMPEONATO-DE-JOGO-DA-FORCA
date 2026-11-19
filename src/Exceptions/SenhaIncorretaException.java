package Exceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException() {
        super("Senha de usuário incorreta!");
    }
}
