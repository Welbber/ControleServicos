package br.com.vital.controle_servico.subscriptions.service.impl;

import br.com.vital.controle_servico.subscriptions.domain.Feature;
import br.com.vital.controle_servico.subscriptions.domain.Subscription;
import br.com.vital.controle_servico.subscriptions.domain.SubscriptionStatus;
import br.com.vital.controle_servico.subscriptions.exception.FeatureNotAllowedException;
import br.com.vital.controle_servico.subscriptions.repository.SubscriptionRepository;
import br.com.vital.controle_servico.subscriptions.repository.TenantAddonRepository;
import br.com.vital.controle_servico.subscriptions.service.FeatureAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeatureAccessServiceImpl implements FeatureAccessService {

    private final SubscriptionRepository subscriptionRepository;
    private final TenantAddonRepository tenantAddonRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean hasAccess(UUID tenantId, Feature feature) {
        boolean hasAddon = tenantAddonRepository.existsByTenantIdAndFeatureAndStatus(
                tenantId, feature, SubscriptionStatus.ACTIVE);
        
        if (hasAddon) {
            return true;
        }

        Optional<Subscription> activeSubscription = subscriptionRepository.findActiveByTenantId(tenantId);
        
        if (activeSubscription.isPresent()) {
            return activeSubscription.get().getPlan().getFeatures().contains(feature);
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public void checkAccess(UUID tenantId, Feature feature) {
        if (!hasAccess(tenantId, feature)) {
            throw new FeatureNotAllowedException("Tenant does not have access to feature: " + feature.name());
        }
    }
}
