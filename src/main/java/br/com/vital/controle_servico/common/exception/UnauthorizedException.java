package br.com.vital.controle_servico.common.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Usuário não identificado");
    }
}
