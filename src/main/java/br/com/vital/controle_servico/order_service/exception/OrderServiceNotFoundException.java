package br.com.vital.controle_servico.order_service.exception;

public class OrderServiceNotFoundException extends RuntimeException {

    public OrderServiceNotFoundException() {
        super("Não foi possível localizar a Ordem de Serviço com o identificador passado");
    }

}
