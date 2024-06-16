package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;

import java.math.BigDecimal;

public record OrderServiceResponseDTO(
        Long id,
        String description,
        OrderServiceType type,
        OrderServiceStatus status,
        String customerName,
        BigDecimal amount,
        Integer quantityItens) {
}
