package br.com.vital.controleServico.vehicles.repository;

import br.com.vital.controleServico.vehicles.domain.Vehicle;
import br.com.vital.controleServico.vehicles.dto.VehicleFilterDTO;
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
public class VehicleCriteriaRepository {

    public static final String LICENSE_PLATE = "licensePlate";
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String YEAR = "year";
    private final EntityManager entityManager;


    public Slice<Vehicle> findAll(VehicleFilterDTO filter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = builder.createQuery(Vehicle.class);

        Root<Vehicle> root = query.from(Vehicle.class);

        List<Predicate> filters = new ArrayList<>();
        if (Objects.nonNull(filter.getBrand())) {
            filters.add(builder.equal(builder.lower(root.get(BRAND)), filter.getBrand().toLowerCase(Locale.ROOT)));
        }

        if (Objects.nonNull(filter.getPlate())) {
            filters.add(builder.equal(builder.lower(root.get(LICENSE_PLATE)), "%" + filter.getPlate().toLowerCase(Locale.ROOT) + "%"));
        }

        if (Objects.nonNull(filter.getYear())) {
            filters.add(builder.equal(root.get(YEAR), filter.getYear()));
        }

        if (Objects.nonNull(filter.getModel())) {
            filters.add(builder.like(builder.lower(root.get(MODEL)), "%" + filter.getModel().toLowerCase(Locale.ROOT) + "%"));
        }

        query.orderBy(builder.asc(root.get(LICENSE_PLATE)));
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
