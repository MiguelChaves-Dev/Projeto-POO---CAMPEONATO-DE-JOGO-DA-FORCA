package Exceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException() {
        super("Esse email não existe!");
    }
}
