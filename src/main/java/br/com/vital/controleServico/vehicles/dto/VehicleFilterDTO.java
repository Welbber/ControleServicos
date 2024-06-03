package br.com.vital.controleServico.vehicles.dto;

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
