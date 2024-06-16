package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderServiceUpdateStatusRequestDTO(
        @NotNull(message = "O Identificador da Ordem de Serviço é obrgatório.")
        Long id,
        @NotNull(message = "O Status da Ordem de Serviço é obrigatório.")
        OrderServiceStatus status,
        @FutureOrPresent(message = "A data de conclusão da Ordem de Serviço não pode ser menor que o dia atual.")
        LocalDate dateEnd) {
}
