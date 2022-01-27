package br.com.vital.controleServico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private String rua;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    private String cidade;

    private String uf;
}
