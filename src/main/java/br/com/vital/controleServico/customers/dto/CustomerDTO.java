package br.com.vital.controleServico.customers.dto;

public record CustomerDTO(Long id, String name, String phoneNumber, String email, String documentNumber,
                          AddressDTO address) {

}