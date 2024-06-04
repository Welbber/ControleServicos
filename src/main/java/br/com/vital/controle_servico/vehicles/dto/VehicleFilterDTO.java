package br.com.vital.controle_servico.vehicles.dto;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class VehicleFilterDTO {

    private String plate;
    private String brand;
    private String model;
    private Integer year;

}
