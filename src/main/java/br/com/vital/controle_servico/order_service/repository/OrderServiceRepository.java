package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.order_service.domain.OrderService;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface OrderServiceRepository extends Repository<OrderService, Long> {

    OrderService saveAndFlush(OrderService orderService);

    OrderService findById(Long id);


}
