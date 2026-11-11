package Exceptions;

public class ContaRepetidaException extends RuntimeException {
    public ContaRepetidaException() {
        super("Conta já existente!");
    }
}
