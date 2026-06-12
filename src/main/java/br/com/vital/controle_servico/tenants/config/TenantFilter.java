package br.com.vital.controle_servico.tenants.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tenantIdStr = request.getHeader(TENANT_HEADER);

        if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
            try {
                TenantContext.setTenantId(UUID.fromString(tenantIdStr));
            } catch (IllegalArgumentException e) {
                logger.warn("Formato de UUID invalido para header " + TENANT_HEADER);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
