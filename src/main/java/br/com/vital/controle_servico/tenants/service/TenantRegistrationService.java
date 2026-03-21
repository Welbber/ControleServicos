package br.com.vital.controle_servico.tenants.service;

import br.com.vital.controle_servico.tenants.config.TenantContext;
import br.com.vital.controle_servico.tenants.domain.Tenant;
import br.com.vital.controle_servico.tenants.dto.TenantRegistrationDTO;
import br.com.vital.controle_servico.tenants.repository.TenantRepository;
import br.com.vital.controle_servico.tenants.exception.TenantAlreadyExistsException;
import br.com.vital.controle_servico.users.exception.UserAlreadyExistsException;
import br.com.vital.controle_servico.users.domain.Role;
import br.com.vital.controle_servico.users.domain.User;
import br.com.vital.controle_servico.users.repository.RoleRepository;
import br.com.vital.controle_servico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantRegistrationService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UUID registerNewTenant(TenantRegistrationDTO dto) {
        
        if (tenantRepository.existsByDocumentNumber(dto.documentNumber())) {
            throw new TenantAlreadyExistsException(String.format("Já existe uma Oficina registrada com o documento (CNPJ/CPF): %s", dto.documentNumber()));
        }

        if (userRepository.existsByUsername(dto.adminUsername())) {
            throw new UserAlreadyExistsException(String.format("O nome de usuário '%s' já está em uso.", dto.adminUsername()));
        }

        if (userRepository.existsByEmail(dto.adminEmail())) {
            throw new UserAlreadyExistsException(String.format("O e-mail '%s' já está vinculado a uma conta.", dto.adminEmail()));
        }

        // 1. Criar e Salvar o Tenant Base
        Tenant tenant = Tenant.builder()
                .tradeName(dto.tradeName())
                .documentNumber(dto.documentNumber())
                .build();
        
        tenant = tenantRepository.save(tenant);
        
        // 2. Definir Forçadamente o ID para a criação do User atado para o interceptor ou SQLRestriction
        TenantContext.setTenantId(tenant.getId());

        // 3. Criar o primeiro USUÁRIO vinculado com Role de ADMIN (Buscando role preexistente ADMIN da migration)
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN não configurada no banco central")); 
        
        User adminUser = User.builder()
                .username(dto.adminUsername())
                .email(dto.adminEmail())
                .password(passwordEncoder.encode(dto.adminPassword()))
                .tenant(tenant)
                .roles(List.of(adminRole)) 
                .build();

        userRepository.save(adminUser);

        // Limpa o Contexto apos transacao principal ser feita
        TenantContext.clear();
        
        return tenant.getId();
    }
}
