package br.com.vital.controle_servico.users.dto;

public record UserRegistrationDTO(
        String username,
        String email,
        String password,
        String roleName 
) {
}
