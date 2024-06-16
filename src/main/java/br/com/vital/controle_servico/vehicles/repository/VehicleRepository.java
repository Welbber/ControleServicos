package br.com.vital.controle_servico.vehicles.repository;

import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface VehicleRepository extends Repository<Vehicle, Long> {

    //TODO: refatora para não retornar todos os dados do customer apenas ID e Nome
    Optional<Vehicle> findById(Long id);

    Vehicle saveAndFlush(Vehicle vehicle);

    @Query("""
            select count(v.id) > 1
            from Vehicle v
            where v.licensePlate = :licensePlate
            """)
    boolean existByLicensePlate(String licensePlate);

    void deleteById(long id);

    List<Vehicle> findByCustomerId(Long customerId);
    
}