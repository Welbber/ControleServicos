package br.com.vital.controle_servico.tenants.dto;

public record TenantRegistrationDTO(
        String tradeName,
        String documentNumber,
        String adminUsername,
        String adminEmail,
        String adminPassword
) {
}
