package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.common.repository.CriteriaRepository;
import br.com.vital.controle_servico.order_service.domain.OrderService;
import br.com.vital.controle_servico.order_service.dto.OrderServiceFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderServiceCriteriaRepository extends CriteriaRepository {

    private final String CUSTOMER = "customer";
    private String DESCRIPTION = "description";
    private String LICENSE_PLATE = "licensePlate";
    private String STATUS = "status";
    private String TYPE = "type";

    public OrderServiceCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional(readOnly = true)
    public Slice<OrderService> findAll(OrderServiceFilterDTO filter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderService> query = builder.createQuery(OrderService.class);

        Root<OrderService> root = query.from(OrderService.class);

        List<Predicate> filters = new ArrayList<>();

        query.orderBy(builder.asc(root.get(DESCRIPTION)));
        query.where(filters.toArray(new Predicate[0]));
        query.distinct(true);

        return getSlicedResult(query, pageable);
    }
}
