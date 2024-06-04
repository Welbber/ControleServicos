package br.com.vital.controle_servico.common.controller;

import br.com.vital.controle_servico.common.exception.CustomerAlreadyExistsException;
import br.com.vital.controle_servico.common.exception.UnauthorizedException;
import br.com.vital.controle_servico.customers.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UnauthorizedException.class
    })
    public ResponseEntity<Object> handleArgumentStateUnauthorized(final RuntimeException ex) {
        final var apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        log.error(apiError.toString());
        return ResponseEntity.status(apiError.getStatus().value()).body(apiError);
    }

    @ExceptionHandler({
            CustomerNotFoundException.class
    })
    public ResponseEntity<Object> handleArgumentStateNotFound(final RuntimeException ex) {
        final var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        log.error(apiError.toString());
        return ResponseEntity.status(apiError.getStatus().value()).body(apiError);
    }


    @ExceptionHandler({
            CustomerAlreadyExistsException.class
    })
    public ResponseEntity<Object> handleArgumentStateBadRequest(final RuntimeException ex) {
        final var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(apiError.toString());
        return ResponseEntity.status(apiError.getStatus().value()).body(apiError);
    }

}
