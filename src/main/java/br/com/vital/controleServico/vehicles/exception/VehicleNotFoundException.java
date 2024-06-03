package br.com.vital.controleServico.vehicles.exception;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException() {
        super("Vehicle Not Found");
    }

}
