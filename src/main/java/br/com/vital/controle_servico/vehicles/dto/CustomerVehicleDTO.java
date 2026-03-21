package br.com.vital.controle_servico.vehicles.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerVehicleDTO(
        @NotNull(message = "Idenficação do Cliente que o veículo pertence é obrigatória")
        UUID customerId,
        String customerName) {
}
