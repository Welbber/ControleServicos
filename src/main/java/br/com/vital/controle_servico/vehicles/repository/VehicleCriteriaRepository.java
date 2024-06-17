package br.com.vital.controle_servico.vehicles.repository;

import br.com.vital.controle_servico.common.repository.CriteriaRepository;
import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import br.com.vital.controle_servico.vehicles.dto.VehicleFilterDTO;
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
import java.util.Locale;
import java.util.Objects;

@Repository
public class VehicleCriteriaRepository extends CriteriaRepository {

    public static final String LICENSE_PLATE = "licensePlate";
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String YEAR = "year";

    public VehicleCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional(readOnly = true)
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

}
