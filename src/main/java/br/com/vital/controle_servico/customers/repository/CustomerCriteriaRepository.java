package br.com.vital.controle_servico.customers.repository;

import br.com.vital.controle_servico.customers.domain.Customer;
import br.com.vital.controle_servico.customers.dto.CustomerFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CustomerCriteriaRepository {

    public static final String DOCUMENT_NUMBER = "documentNumber";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    private final EntityManager entityManager;

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

    private <T> SliceImpl<T> getSlicedResult(final CriteriaQuery<T> query, final Pageable pageable) {
        int pageSize = pageable.getPageSize();
        final TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageSize + 1);

        final List<T> resultList = typedQuery.getResultList();
        boolean hasNext = resultList.size() > pageSize;

        return new SliceImpl<>(hasNext ? resultList.subList(0, pageSize) : resultList, pageable, hasNext);
    }

}
