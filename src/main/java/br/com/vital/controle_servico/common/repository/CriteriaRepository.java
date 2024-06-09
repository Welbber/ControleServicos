package br.com.vital.controle_servico.common.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CriteriaRepository {

    public final EntityManager entityManager;

    public <T> SliceImpl<T> getSlicedResult(final CriteriaQuery<T> query, final Pageable pageable) {
        int pageSize = pageable.getPageSize();
        final TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageSize + 1);

        final List<T> resultList = typedQuery.getResultList();
        boolean hasNext = resultList.size() > pageSize;

        return new SliceImpl<>(hasNext ? resultList.subList(0, pageSize) : resultList, pageable, hasNext);
    }

}
