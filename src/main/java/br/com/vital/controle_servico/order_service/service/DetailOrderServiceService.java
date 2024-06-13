package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.order_service.dto.DetailOrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.repository.OrderServiceDetailCustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DetailOrderServiceService {

    private final OrderServiceDetailCustomerRepository orderServiceDetailFindRepository;

    public DetailOrderServiceResponseDTO detail(Long orderServiceId) {
        log.info("Consult received to detail for order with id: {}", orderServiceId);
        var detail = orderServiceDetailFindRepository.findByOrderServiceId(orderServiceId);
        log.info("Result received to detail for order with id: {}", detail);
        return detail;
    }
}
