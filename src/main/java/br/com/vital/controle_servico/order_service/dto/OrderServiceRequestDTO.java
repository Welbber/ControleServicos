package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.itens.dto.ItemOrderRequestDTO;
import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderServiceRequestDTO(
        @NotNull(message = "A descrição da Ordem de Serviço precisa se informada")
        String description,
        @NotNull(message = "O Tipo da Ordem de Serviço é obrigatório")
        OrderServiceType type,
        @NotNull(message = "O Status da Ordem de Serviço é obrigatório")
        OrderServiceStatus status,
        @NotNull(message = "O Veículo associada a Ordem de Serviço é um obrigatório")
        Long vehicleId,
        BigDecimal amount,
        @NotNull(message = "A Kilometragem atual do Veículo é obrigatório")
        Integer kmVehicleAt,
        LocalDate dateStart,
        @Valid
        @NotNull(message = "A lista de itens é obrigatório")
        List<ItemOrderRequestDTO> itens
) {
}
