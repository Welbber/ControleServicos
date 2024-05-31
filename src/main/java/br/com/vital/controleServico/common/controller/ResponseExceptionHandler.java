package br.com.vital.controleServico.common.controller;

import br.com.vital.controleServico.common.exception.UnauthorizedException;
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


}
