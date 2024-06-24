package br.com.vital.controle_servico.common.controller;

import br.com.vital.controle_servico.common.exception.UnauthorizedException;
import br.com.vital.controle_servico.customers.exception.CustomerAlreadyExistsException;
import br.com.vital.controle_servico.customers.exception.CustomerNotFoundException;
import br.com.vital.controle_servico.itens.exception.ItemAlreadyExistsException;
import br.com.vital.controle_servico.itens.exception.ItemNotFoundException;
import br.com.vital.controle_servico.order_service.exception.OrderServiceNotFoundException;
import br.com.vital.controle_servico.order_service.exception.VehicleNotBelongToCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler({
            UnauthorizedException.class
    })
    public ResponseEntity<Object> handleArgumentStateUnauthorized(final RuntimeException ex) {
        final var apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return handleArgumentState(apiError);
    }

    @ExceptionHandler({
            CustomerNotFoundException.class,
            ItemNotFoundException.class,
            OrderServiceNotFoundException.class
    })
    public ResponseEntity<Object> handleArgumentStateNotFound(final RuntimeException ex) {
        final var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleArgumentState(apiError);
    }


    @ExceptionHandler({
            CustomerAlreadyExistsException.class,
            ItemAlreadyExistsException.class,
            VehicleNotBelongToCustomer.class,
            IllegalStateException.class
    })
    public ResponseEntity<Object> handleArgumentStateBadRequest(final RuntimeException ex) {
        final var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleArgumentState(apiError);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        final var messages = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
        final var apiError = new ApiError(HttpStatus.BAD_REQUEST, messages);
        return handleArgumentState(apiError);

    }

    private ResponseEntity<Object> handleArgumentState(ApiError apiError) {
        log.error(apiError.toString());
        return ResponseEntity.status(apiError.getStatus().value()).body(apiError);
    }

}
