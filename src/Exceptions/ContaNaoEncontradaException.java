package Exceptions;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException() {
        super("O login ou senha estão errados!");
    }
}
