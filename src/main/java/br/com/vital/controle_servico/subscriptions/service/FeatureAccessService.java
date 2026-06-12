package br.com.vital.controle_servico.subscriptions.service;

import br.com.vital.controle_servico.subscriptions.domain.Feature;

import java.util.UUID;

public interface FeatureAccessService {

    boolean hasAccess(UUID tenantId, Feature feature);

    void checkAccess(UUID tenantId, Feature feature);
}
