package br.com.vital.controle_servico.order_service.dto;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;
import lombok.*;


@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceFilterDTO {

    private String customer;
    private String description;
    private String licensePlate;
    private OrderServiceStatus status;
    private OrderServiceType type;

}
