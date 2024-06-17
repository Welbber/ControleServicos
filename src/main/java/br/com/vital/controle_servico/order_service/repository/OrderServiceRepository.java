package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.order_service.domain.OrderService;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface OrderServiceRepository extends Repository<OrderService, Long> {

    @Transactional
    OrderService saveAndFlush(OrderService orderService);

    @Transactional(readOnly = true)
    Optional<OrderService> findById(Long id);

}
