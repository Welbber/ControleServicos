package br.com.vital.controle_servico.tenants.repository;

import br.com.vital.controle_servico.tenants.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    boolean existsByDocumentNumber(String documentNumber);
}
