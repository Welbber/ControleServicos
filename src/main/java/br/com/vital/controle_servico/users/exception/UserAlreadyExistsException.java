package br.com.vital.controle_servico.users.exception;

public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
