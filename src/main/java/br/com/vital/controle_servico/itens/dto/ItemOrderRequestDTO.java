package br.com.vital.controle_servico.itens.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemOrderRequestDTO(
        @NotNull(message = "O Item é obrigatório")
        Long id, BigDecimal saleAmount, Integer quantity) {
}
