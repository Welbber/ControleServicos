package br.com.vital.controle_servico.itens.repository;

import br.com.vital.controle_servico.common.repository.CriteriaRepository;
import br.com.vital.controle_servico.itens.domain.Item;
import br.com.vital.controle_servico.itens.dto.ItemFilterDTO;
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
public class ItemCriteriaRepository extends CriteriaRepository {

    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String TYPE = "type";

    public ItemCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Slice<Item> findAll(ItemFilterDTO filter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = builder.createQuery(Item.class);

        Root<Item> root = query.from(Item.class);

        List<Predicate> filters = new ArrayList<>();

        if (Objects.nonNull(filter.getCode())) {
            filters.add(builder.like(builder.lower(root.get(CODE)), "%" + filter.getCode().toLowerCase(Locale.ROOT) + "%"));
        }

        if (Objects.nonNull(filter.getDescription())) {
            filters.add(builder.like(builder.lower(root.get(DESCRIPTION)), "%" + filter.getDescription().toLowerCase(Locale.ROOT) + "%"));
        }

        if (Objects.nonNull(filter.getType())) {
            filters.add(builder.like(builder.lower(root.get(TYPE)), "%" + filter.getType().toLowerCase(Locale.ROOT) + "%"));
        }

        query.orderBy(builder.asc(root.get(DESCRIPTION)));
        query.where(filters.toArray(new Predicate[0]));
        query.distinct(true);

        return getSlicedResult(query, pageable);
    }
}
