package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.order_service.domain.OrderServiceDetail;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface OrderServiceDetailRepository extends Repository<OrderServiceDetail, Long> {

    @Transactional
    void saveAllAndFlush(Iterable<OrderServiceDetail> ordersServiceDetails);

}
