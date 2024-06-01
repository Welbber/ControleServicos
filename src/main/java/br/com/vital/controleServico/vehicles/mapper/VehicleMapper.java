package br.com.vital.controleServico.vehicles.mapper;

import br.com.vital.controleServico.customers.domain.Customer;
import br.com.vital.controleServico.vehicles.domain.Vehicle;
import br.com.vital.controleServico.vehicles.dto.CustomerVehicleDTO;
import br.com.vital.controleServico.vehicles.dto.VehicleDTO;
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
                vehicle.getYear()
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
                .build();
    }

    private static Customer toCustomer(CustomerVehicleDTO customerVehicle) {
        return new Customer(customerVehicle.customerId());
    }

}
