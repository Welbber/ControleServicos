package br.com.vital.controle_servico.itens.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import java.util.UUID;

public record ItemOrderRequestDTO(
        @NotNull(message = "O Item é obrigatório")
        UUID id, BigDecimal saleAmount, Integer quantity) {
}
