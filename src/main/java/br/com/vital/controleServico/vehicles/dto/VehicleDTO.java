package br.com.vital.controleServico.vehicles.dto;

import br.com.vital.controleServico.vehicles.domain.FuelType;

public record VehicleDTO(
        Long id,
        CustomerVehicleDTO customerVehicle,
        String brand,
        String model,
        String plate,
        String color,
        Integer year,
        FuelType fuelType) {
}
