package br.com.vital.controle_servico.customers.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Cliente não encontrado.");
    }
}
