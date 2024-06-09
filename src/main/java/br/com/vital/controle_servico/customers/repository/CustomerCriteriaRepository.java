package br.com.vital.controle_servico.customers.repository;

import br.com.vital.controle_servico.common.repository.CriteriaRepository;
import br.com.vital.controle_servico.customers.domain.Customer;
import br.com.vital.controle_servico.customers.dto.CustomerFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@Component
public class CustomerCriteriaRepository extends CriteriaRepository {

    public static final String DOCUMENT_NUMBER = "documentNumber";
    public static final String EMAIL = "email";
    public static final String NAME = "name";

    public CustomerCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Slice<Customer> findAll(CustomerFilterDTO filter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);

        Root<Customer> root = query.from(Customer.class);

        List<Predicate> filters = new ArrayList<>();
        if (Objects.nonNull(filter.getDocumentNumber())) {
            filters.add(builder.equal(root.get(DOCUMENT_NUMBER), filter.getDocumentNumber()));
        }

        if (Objects.nonNull(filter.getName())) {
            filters.add(builder.like(builder.lower(root.get(NAME)), "%" + filter.getName().toLowerCase(Locale.ROOT) + "%"));
        }

        if (Objects.nonNull(filter.getEmail())) {
            filters.add(builder.like(builder.lower(root.get(EMAIL)), "%" + filter.getEmail().toLowerCase(Locale.ROOT) + "%"));
        }

        query.orderBy(builder.asc(root.get(NAME)));
        query.where(filters.toArray(new Predicate[0]));
        query.distinct(true);

        return getSlicedResult(query, pageable);
    }


}
