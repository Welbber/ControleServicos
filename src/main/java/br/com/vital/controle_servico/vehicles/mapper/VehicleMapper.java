package br.com.vital.controle_servico.vehicles.mapper;

import br.com.vital.controle_servico.customers.domain.Customer;
import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import br.com.vital.controle_servico.vehicles.dto.CustomerVehicleDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleMapper {

    public static VehicleDTO toVehicleDTO(Vehicle vehicle) {
        return new VehicleDTO(
                vehicle.getId(),
                toCustomerVehicle(vehicle.getCustomer()),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getLicensePlate(),
                vehicle.getColor(),
                vehicle.getYear(),
                vehicle.getFuelType()
        );
    }

    private static CustomerVehicleDTO toCustomerVehicle(Customer customer) {
        return new CustomerVehicleDTO(customer.getId(), customer.getName());
    }

    public static Vehicle toVehicle(VehicleDTO vehicleDTO) {
        return Vehicle.builder()
                .id(vehicleDTO.id())
                .customer(toCustomer(vehicleDTO.customerVehicle()))
                .brand(vehicleDTO.brand())
                .model(vehicleDTO.model())
                .color(vehicleDTO.color())
                .year(vehicleDTO.year())
                .licensePlate(vehicleDTO.plate())
                .fuelType(vehicleDTO.fuelType())
                .build();
    }

    private static Customer toCustomer(CustomerVehicleDTO customerVehicle) {
        return new Customer(customerVehicle.customerId());
    }

}
