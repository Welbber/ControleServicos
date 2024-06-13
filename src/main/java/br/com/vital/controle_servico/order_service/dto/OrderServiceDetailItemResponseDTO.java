package br.com.vital.controle_servico.order_service.dto;

import java.math.BigDecimal;

public record OrderServiceDetailItemResponseDTO(
        String code,
        String description,
        Integer quantity,
        BigDecimal saleAmount) {
}
