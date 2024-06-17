package br.com.vital.controle_servico.customers.dto;

public record CustomerResponseDTO(
        Long id,
        String name,
        String phoneNumber,
        String email,
        String documentNumber,
        AddressDTO address) {

}