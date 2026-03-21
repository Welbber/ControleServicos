package br.com.vital.controle_servico.tenants.exception;

public class TenantAlreadyExistsException extends RuntimeException {
    
    public TenantAlreadyExistsException(String message) {
        super(message);
    }
}
