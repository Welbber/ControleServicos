package br.com.vital.controle_servico.order_service.mapper;

import br.com.vital.controle_servico.itens.dto.ItemOrderRequestDTO;
import br.com.vital.controle_servico.order_service.domain.OrderService;
import br.com.vital.controle_servico.order_service.dto.OrderServiceRequestDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceMapper {

    public static OrderService toOrderService(OrderServiceRequestDTO orderServiceRequestDTO) {
        return OrderService.builder()
                .description(orderServiceRequestDTO.description())
                .type(orderServiceRequestDTO.type())
                .status(orderServiceRequestDTO.status())
                .quantityItems(orderServiceRequestDTO.itens().stream().mapToInt(ItemOrderRequestDTO::quantity).sum())
                .amount(getAmountTotal(orderServiceRequestDTO))
                .kmVehicleAt(orderServiceRequestDTO.kmVehicleAt())
                .dateStart(orderServiceRequestDTO.dateStart())
                .build();
    }

    private static BigDecimal getAmountTotal(OrderServiceRequestDTO orderServiceRequestDTO) {
        return orderServiceRequestDTO.itens().stream()
                .filter(Objects::nonNull)
                .map(item -> item.saleAmount().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static OrderServiceResponseDTO toResponseDTO(OrderService orderService) {
        return new OrderServiceResponseDTO(
                orderService.getId(),
                orderService.getDescription(),
                orderService.getType(),
                orderService.getStatus(),
                orderService.getCustomer().getName(),
                orderService.getAmount(),
                orderService.getQuantityItems());
    }

}
