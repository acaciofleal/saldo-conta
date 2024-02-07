package br.com.neocamp.saldo.conta.exception;

public class ContaBancariaNotFoundException extends RuntimeException {
    public ContaBancariaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
