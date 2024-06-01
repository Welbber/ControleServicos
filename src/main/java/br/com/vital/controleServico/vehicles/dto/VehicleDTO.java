package br.com.vital.controleServico.vehicles.dto;

public record VehicleDTO(
        Long id,
        CustomerVehicleDTO customerVehicle,
        String brand,
        String model,
        String plate,
        String color,
        Integer year) {
}
