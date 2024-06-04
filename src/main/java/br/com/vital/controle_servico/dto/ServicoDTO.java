package br.com.vital.controle_servico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoDTO {

    private Long id;

    private Long idVeiculo;

    private String descricaoGeral;

    private String status;

    private LocalDate dataCadastro;
}