package br.com.vital.controle_servico.users.controller;

import br.com.vital.controle_servico.tenants.config.TenantContext;
import br.com.vital.controle_servico.users.domain.Role;
import br.com.vital.controle_servico.users.domain.User;
import br.com.vital.controle_servico.users.dto.UserRegistrationDTO;
import br.com.vital.controle_servico.users.repository.RoleRepository;
import br.com.vital.controle_servico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Apenas Administradores podem cadastrar novos funcionarios
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Map<String, String>> registerEmployee(@RequestBody UserRegistrationDTO dto) {
        
        // Tenant resolvido automaticamente pelo TenantFilter a partir do claim "tenantId" do JWT do usuário autenticado
        UUID currentTenant = TenantContext.getTenantId();
        
        if (currentTenant == null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "TenantContext inexistente"));
        }

        Role assignedRole = roleRepository.findByName(dto.roleName())
                .orElseThrow(() -> new IllegalArgumentException("Role " + dto.roleName() + " informada não existe"));

        User newEmployee = User.builder()
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .roles(List.of(assignedRole))
                .build();

        userRepository.save(newEmployee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Funcionário registrado com sucesso na Oficina."));
    }
}
