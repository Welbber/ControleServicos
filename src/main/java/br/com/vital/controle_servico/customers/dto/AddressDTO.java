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
                         @NotNull(message = "CEP é obrigatório")
                         @Pattern(regexp = "^\\d{5}-\\d{3}$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE}, message = "CEP com formato inválido.")
                         String zipCode) {
}
