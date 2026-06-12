package br.com.vital.controle_servico.tenants.config;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.lang.reflect.Field;
import java.util.UUID;

public class TenantEntityListener {

    @PrePersist
    @PreUpdate
    public void setTenantId(Object entity) {
        UUID tenantId = TenantContext.getTenantId();
        
        if (tenantId == null) {
            return;
        }
        
        try {

            Field tenantIdField = entity.getClass().getDeclaredField("tenantId");
            tenantIdField.setAccessible(true);

            if (tenantIdField.get(entity) == null) {
               tenantIdField.set(entity, tenantId);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }
    }
}
