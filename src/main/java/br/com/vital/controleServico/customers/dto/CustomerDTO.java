package br.com.vital.controleServico.customers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDTO(Long id,
                          @NotNull(message = "Name is mandatory")
                          String name,
                          @Size(max = 11, message = "Number max character is 11")
                          String phoneNumber,
                          @NotNull(message = "Email is mandatory")
                          @Email(message = "The email address is invalid.", flags = {Pattern.Flag.CASE_INSENSITIVE})
                          String email,
                          @Size(max = 14, min = 11, message = "Document should size 11 for (CPF) or 14(CNPJ)")
                          @NotNull(message = "DocumentNumber is mandatory")
                          String documentNumber,
                          @Valid
                          @NotNull(message = "Address is mandatory")
                          AddressDTO address) {

}