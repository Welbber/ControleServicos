package br.com.vital.controle_servico.vehicles.exception;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException() {
        super("Vehicle Not Found");
    }

}
