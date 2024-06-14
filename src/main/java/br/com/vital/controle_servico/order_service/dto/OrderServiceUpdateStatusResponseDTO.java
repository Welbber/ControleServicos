package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;

import java.time.LocalDate;

public record OrderServiceUpdateStatusResponseDTO(
        String description,
        OrderServiceStatus oldStatus,
        OrderServiceStatus newStatus,
        LocalDate dateEnd) {
}
