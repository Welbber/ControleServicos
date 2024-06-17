package br.com.vital.controle_servico.vehicles.mapper;

import br.com.vital.controle_servico.customers.domain.Customer;
import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import br.com.vital.controle_servico.vehicles.dto.CustomerVehicleDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleRequestDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleMapper {

    public static VehicleResponseDTO toVehicleDTO(Vehicle vehicle) {
        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getCustomer().getName(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getLicensePlate(),
                vehicle.getColor(),
                vehicle.getYear(),
                vehicle.getFuelType()
        );
    }

    public static Vehicle toVehicle(VehicleRequestDTO vehicleRequestDTO) {
        return Vehicle.builder()
                .customer(toCustomer(vehicleRequestDTO.customerVehicle()))
                .brand(vehicleRequestDTO.brand())
                .model(vehicleRequestDTO.model())
                .color(vehicleRequestDTO.color())
                .year(vehicleRequestDTO.year())
                .licensePlate(vehicleRequestDTO.plate())
                .fuelType(vehicleRequestDTO.fuelType())
                .build();
    }

    private static Customer toCustomer(CustomerVehicleDTO customerVehicle) {
        return new Customer(customerVehicle.customerId());
    }

}
