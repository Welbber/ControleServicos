package br.com.vital.controle_servico.vehicles.dto;

import jakarta.validation.constraints.NotNull;

public record CustomerVehicleDTO(
        @NotNull(message = "Idenficação do Cliente que o veículo pertence é obrigatória")
        Long customerId,
        String customerName) {
}
