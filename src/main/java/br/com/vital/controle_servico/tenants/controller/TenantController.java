package br.com.vital.controle_servico.tenants.controller;

import br.com.vital.controle_servico.tenants.dto.TenantRegistrationDTO;
import br.com.vital.controle_servico.tenants.service.TenantRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody TenantRegistrationDTO request) {
        UUID tenantId = registrationService.registerNewTenant(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Oficina criada com sucesso!", "tenantId", tenantId.toString()));
    }
}
