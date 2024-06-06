package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;

public record OrderServiceDTO(
        Long id,
        String description,
        OrderServiceType type,
        OrderServiceStatus status) {
}
