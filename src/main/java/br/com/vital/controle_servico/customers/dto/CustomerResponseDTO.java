package br.com.vital.controle_servico.customers.dto;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String phoneNumber,
        String email,
        String documentNumber,
        AddressDTO address) {

}