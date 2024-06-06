package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.order_service.dto.OrderServiceDTO;
import br.com.vital.controle_servico.order_service.repository.OrderServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceService {

    private final OrderServiceRepository orderServiceRepository;

    @Transactional
    public OrderServiceDTO save(OrderServiceDTO orderServiceDTO) {
        return null;
    }

}
