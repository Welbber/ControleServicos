package br.com.vital.controle_servico.itens.dto;

import br.com.vital.controle_servico.itens.domain.ItemType;

import java.math.BigDecimal;

public record ItemResponseDTO(Long id,
                              String code,
                              String description,
                              ItemType type,
                              BigDecimal saleAmount,
                              Integer quantity) {
}
