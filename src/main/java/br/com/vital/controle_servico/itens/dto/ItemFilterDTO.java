package br.com.vital.controle_servico.itens.dto;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ItemFilterDTO {

    private String code;
    private String description;
    private String type;

}
