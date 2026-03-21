package br.com.vital.controle_servico.vehicles.dto;

import br.com.vital.controle_servico.vehicles.domain.FuelType;

import java.util.UUID;

public record VehicleResponseDTO(
        UUID id,
        String customerName,
        String brand,
        String model,
        String plate,
        String color,
        Integer year,
        FuelType fuelType) {
}
