package br.com.vital.controle_servico.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteQuantidadeServicosDTO {

    private Long id;

    @NotBlank(message = "Nome Cliente não pode ser nulo")
    private String nome;

    private String telefone;

    private Integer quantiCarrosCadastrados;

}
