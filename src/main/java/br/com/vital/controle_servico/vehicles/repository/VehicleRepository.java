package br.com.vital.controle_servico.vehicles.repository;

import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface VehicleRepository extends Repository<Vehicle, Long> {

    @Transactional(readOnly = true)
    Optional<Vehicle> findById(Long id);

    @Transactional
    Vehicle saveAndFlush(Vehicle vehicle);

    @Transactional(readOnly = true)
    @Query("""
            select count(v.id) > 1
            from Vehicle v
            where v.licensePlate = :licensePlate
            """)
    boolean existByLicensePlate(String licensePlate);

    @Transactional
    void deleteById(long id);

    @Transactional(readOnly = true)
    List<Vehicle> findByCustomerId(Long customerId);

}
