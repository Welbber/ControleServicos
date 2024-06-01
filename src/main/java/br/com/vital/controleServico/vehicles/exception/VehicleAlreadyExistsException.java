package br.com.vital.controleServico.vehicles.exception;

public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
    
}
