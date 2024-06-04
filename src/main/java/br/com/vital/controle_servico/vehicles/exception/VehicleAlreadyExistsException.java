package br.com.vital.controle_servico.vehicles.exception;

public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException(String message) {
        super(message);
    }

}
