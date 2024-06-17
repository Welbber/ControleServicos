package br.com.vital.controle_servico.vehicles.dto;

import br.com.vital.controle_servico.vehicles.domain.FuelType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestDTO(
        @Valid
        CustomerVehicleDTO customerVehicle,
        String brand,
        String model,
        @NotNull(message = "A placa do veículo é obrigatório")
        String plate,
        String color,
        Integer year,
        FuelType fuelType) {
}
