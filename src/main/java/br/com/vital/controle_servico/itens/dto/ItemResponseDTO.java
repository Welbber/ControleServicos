package br.com.vital.controle_servico.itens.dto;

import br.com.vital.controle_servico.itens.domain.ItemType;

import java.math.BigDecimal;

import java.util.UUID;

public record ItemResponseDTO(UUID id,
                              String code,
                              String description,
                              ItemType type,
                              BigDecimal saleAmount,
                              Integer quantity) {
}
