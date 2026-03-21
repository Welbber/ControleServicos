package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.order_service.domain.OrderService;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface OrderServiceRepository extends Repository<OrderService, UUID> {

    @Transactional
    OrderService saveAndFlush(OrderService orderService);

    @Transactional(readOnly = true)
    Optional<OrderService> findById(UUID id);

}
