package br.com.vital.controle_servico.auth.exception;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;

public class CustomJwtException extends JwtException {

    public CustomJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public static CustomJwtException getCustomJwtException(Exception exception) {
        CustomJwtException customJwtException = null;
        if (exception instanceof BadCredentialsException) {
            customJwtException = new CustomJwtException("As credenciais informadas não são validas", exception);
        }
        if (exception instanceof BadJwtException) {
            customJwtException = new CustomJwtException("O Token está invalido, não foi possível decodificar.", exception);
        }
        if (exception instanceof JwtValidationException) {
            customJwtException = new CustomJwtException("O Token informado está expirado", exception);
        }
        if (exception instanceof JwtEncodingException) {
            customJwtException = new CustomJwtException("Não foi possível decodificar o token", exception);
        }
        return customJwtException;
    }

}
