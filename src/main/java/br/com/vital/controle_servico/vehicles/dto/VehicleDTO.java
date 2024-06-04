package br.com.vital.controle_servico.vehicles.dto;

import br.com.vital.controle_servico.vehicles.domain.FuelType;

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
