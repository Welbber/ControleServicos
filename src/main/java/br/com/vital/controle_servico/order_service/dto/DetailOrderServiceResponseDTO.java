package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;


public record DetailOrderServiceResponseDTO(
        Long id,
        String description,
        BigDecimal amountTotal,
        Integer kmVehicleAt,
        Integer quantityItemsTotal,
        OrderServiceType type,
        OrderServiceStatus status,
        Long customerId,
        String customerEmail,
        String customerName,
        String licensePlate,
        @ToString.Exclude
        List<OrderServiceDetailItemResponseDTO> items) {
}
