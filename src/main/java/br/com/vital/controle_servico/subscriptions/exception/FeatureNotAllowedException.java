package br.com.vital.controle_servico.subscriptions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class FeatureNotAllowedException extends RuntimeException {

    public FeatureNotAllowedException(String message) {
        super(message);
    }
}
