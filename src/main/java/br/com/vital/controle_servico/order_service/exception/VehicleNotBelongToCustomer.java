package br.com.vital.controle_servico.order_service.exception;

public class VehicleNotBelongToCustomer extends RuntimeException {

    public VehicleNotBelongToCustomer(String message) {
        super(message);
    }

}
