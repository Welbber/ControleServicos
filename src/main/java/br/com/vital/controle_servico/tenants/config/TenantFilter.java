package br.com.vital.controle_servico.tenants.config;

import br.com.vital.controle_servico.auth.service.UserAuthenticated;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // O tenantId é resolvido exclusivamente a partir do usuário autenticado (claim do JWT,
        // validado por assinatura em JwtAuthFilter), nunca a partir de headers/parâmetros
        // informados pelo cliente - evita que um usuário declare-se de outro tenant.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserAuthenticated userAuthenticated
                && userAuthenticated.getTenantId() != null) {
            try {
                TenantContext.setTenantId(UUID.fromString(userAuthenticated.getTenantId()));
            } catch (IllegalArgumentException e) {
                logger.warn("Formato de UUID invalido para tenantId do usuário autenticado");
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
