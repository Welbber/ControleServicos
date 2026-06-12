package br.com.vital.controle_servico.vehicles.repository;

import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface VehicleRepository extends Repository<Vehicle, UUID> {

    @Transactional(readOnly = true)
    Optional<Vehicle> findById(UUID id);

    @Transactional
    Vehicle saveAndFlush(Vehicle vehicle);

    @Transactional(readOnly = true)
    boolean existsByLicensePlate(String licensePlate);

    @Transactional
    void deleteById(UUID id);

    @Transactional(readOnly = true)
    List<Vehicle> findByCustomerId(UUID customerId);

}
