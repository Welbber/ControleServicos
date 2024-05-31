package br.com.vital.controleServico.customers.dto;

import br.com.vital.controleServico.dto.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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