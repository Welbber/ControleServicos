package br.com.vital.controleServico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteQuantidadeServicosDTO {

    private String nome;

    private String telefone;

    private Integer quantiCarrosCadastrados;

    private Integer quantidadeServicos;
}
