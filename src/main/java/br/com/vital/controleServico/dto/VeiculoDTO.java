package br.com.vital.controleServico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private Long id;

    private Long idCliente;

    private String modelo;

    private String placa;

    private Integer kilometgragemInicial;

    private Integer kilometgragemFinal;

    private LocalDate dataCadastro;

}
