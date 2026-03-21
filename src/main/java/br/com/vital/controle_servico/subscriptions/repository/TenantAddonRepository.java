package br.com.vital.controle_servico.subscriptions.repository;

import br.com.vital.controle_servico.subscriptions.domain.Feature;
import br.com.vital.controle_servico.subscriptions.domain.SubscriptionStatus;
import br.com.vital.controle_servico.subscriptions.domain.TenantAddon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TenantAddonRepository extends JpaRepository<TenantAddon, UUID> {

    List<TenantAddon> findByTenantIdAndStatus(UUID tenantId, SubscriptionStatus status);

    boolean existsByTenantIdAndFeatureAndStatus(UUID tenantId, Feature feature, SubscriptionStatus status);
}
