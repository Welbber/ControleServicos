package br.com.vital.controleServico.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    private String nome;

    private String telefone;

    private String email;

    private LocalDate dataCadastro;

    private EnderecoDTO endereco;
}