package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.order_service.domain.OrderServiceDetail;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface OrderServiceDetailRepository extends Repository<OrderServiceDetail, Long> {

    void saveAllAndFlush(Iterable<OrderServiceDetail> ordersServiceDetails);

}
