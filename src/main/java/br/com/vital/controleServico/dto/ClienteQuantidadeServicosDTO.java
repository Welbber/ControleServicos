package br.com.vital.controleServico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteQuantidadeServicosDTO  {

    private Long id;

    @NotBlank(message = "Nome Cliente não pode ser nulo")
    private String nome;

    private String telefone;

    private Integer quantiCarrosCadastrados;

}
