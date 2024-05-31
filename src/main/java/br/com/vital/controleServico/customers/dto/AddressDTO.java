package br.com.vital.controleServico.customers.dto;

public record AddressDTO(String street,
                         Integer number,
                         String complement,
                         String city,
                         String state,
                         String zipCode) {
}
