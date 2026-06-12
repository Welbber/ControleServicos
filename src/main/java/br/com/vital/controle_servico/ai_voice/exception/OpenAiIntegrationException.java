package br.com.vital.controle_servico.ai_voice.exception;

public class OpenAiIntegrationException extends RuntimeException {
    
    public OpenAiIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
