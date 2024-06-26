package br.com.vital.controle_servico.common.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ApiError {

    private List<String> messages;

    private Integer code;

    @JsonIgnoreProperties
    private HttpStatus status;

    public ApiError(final HttpStatus status,
                    final String message) {
        this.messages = List.of(message);
        this.code = status.value();
        this.status = status;
    }

    public ApiError(final HttpStatus status,
                    final List<String> messages) {
        this.messages = messages;
        this.code = status.value();
        this.status = status;
    }
}
