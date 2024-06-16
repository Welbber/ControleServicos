package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.order_service.dto.OrderServiceUpdateStatusRequestDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceUpdateStatusResponseDTO;
import br.com.vital.controle_servico.order_service.exception.OrderServiceNotFoundException;
import br.com.vital.controle_servico.order_service.repository.OrderServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateStatusOrderServiceService {

    private final OrderServiceRepository repository;

    @Transactional
    public OrderServiceUpdateStatusResponseDTO updateStatusOrCloseOrder(OrderServiceUpdateStatusRequestDTO requestDTO) {
        log.info("Executing updateStatus for order with id: {}", requestDTO.id());

        var orderService = repository.findById(requestDTO.id())
                .orElseThrow(OrderServiceNotFoundException::new);
        log.info("Order with id: {} found", orderService);

        var oldStatus = orderService.getStatus();

        orderService.changeStatus(requestDTO.status());
        orderService.closeOrder(requestDTO.status(), requestDTO.dateEnd());
        log.info("Order: {} updating", orderService);

        repository.saveAndFlush(orderService);

        return new OrderServiceUpdateStatusResponseDTO(
                orderService.getDescription(),
                oldStatus,
                orderService.getStatus(),
                orderService.getDateEnd());
    }


}
