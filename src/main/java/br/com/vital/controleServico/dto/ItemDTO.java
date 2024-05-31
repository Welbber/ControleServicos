package br.com.vital.controleServico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {

    private Long id;

    private Long idServico;

    private String tipo;

    private LocalDate dataCadastro;
}
