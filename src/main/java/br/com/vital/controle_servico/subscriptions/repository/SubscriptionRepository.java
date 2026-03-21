package br.com.vital.controle_servico.subscriptions.repository;

import br.com.vital.controle_servico.subscriptions.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query("SELECT s FROM Subscription s JOIN FETCH s.plan p LEFT JOIN FETCH p.features WHERE s.tenant.id = :tenantId AND s.status = 'ACTIVE'")
    Optional<Subscription> findActiveByTenantId(@Param("tenantId") UUID tenantId);
}
