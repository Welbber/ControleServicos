package br.com.vital.controle_servico.customers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(Long id,
                         String street,
                         Integer number,
                         String complement,
                         String city,
                         String state,
                         String district,
                         @NotNull(message = "Zipcode is mandatory")
                         @Pattern(regexp = "^\\d{1,5}$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE}, message = "The Zip code is invalid.")
                         String zipCode) {
}
