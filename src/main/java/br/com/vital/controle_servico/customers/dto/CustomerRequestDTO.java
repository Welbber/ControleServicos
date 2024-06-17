package br.com.vital.controle_servico.customers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(
        @NotNull(message = "Nome do cliente é obrigatório")
        String name,
        @Size(max = 11, message = "Número máximo do telefone é 11 digitos")
        String phoneNumber,
        @NotNull(message = "E-mail do cliente é obrigatório")
        @Email(message = "E-mail inválido.", flags = {Pattern.Flag.CASE_INSENSITIVE})
        String email,
        @Size(max = 14, min = 11, message = "O documento do cliente deve ter 11 para (CPF) ou 14 para (CNPJ)")
        @NotNull(message = "Documento de identificação é obrigatório ")
        String documentNumber,
        @Valid
        @NotNull(message = "Endereço do cliente é obrigatório")
        AddressDTO address) {

}