package br.com.vital.controleServico.customers.dto;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CustomerFilterDTO {

    private String name;
    private String documentNumber;
    private String email;

}
