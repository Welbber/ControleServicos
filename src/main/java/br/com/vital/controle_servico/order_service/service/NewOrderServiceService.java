package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.customers.domain.Customer;
import br.com.vital.controle_servico.customers.exception.CustomerNotFoundException;
import br.com.vital.controle_servico.customers.repository.CustomerRepository;
import br.com.vital.controle_servico.order_service.dto.OrderServiceRequestDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.exception.VehicleNotBelongToCustomer;
import br.com.vital.controle_servico.order_service.mapper.OrderDetailMapper;
import br.com.vital.controle_servico.order_service.mapper.OrderServiceMapper;
import br.com.vital.controle_servico.order_service.repository.OrderServiceDetailRepository;
import br.com.vital.controle_servico.order_service.repository.OrderServiceRepository;
import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import br.com.vital.controle_servico.vehicles.exception.VehicleNotFoundException;
import br.com.vital.controle_servico.vehicles.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewOrderServiceService {

    private final OrderServiceRepository repository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final OrderServiceDetailRepository orderServiceDetailRepository;

    @Transactional
    public OrderServiceResponseDTO save(Long customerId, OrderServiceRequestDTO orderServiceRequestDTO) {
        log.info("Received request to save a new order: {} to customerId: {}", orderServiceRequestDTO, customerId);

        var customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        log.info("Customer found: {}", customer);

        var vehicle = vehicleRepository.findById(orderServiceRequestDTO.vehicleId())
                .orElseThrow(VehicleNotFoundException::new);
        log.info("Vehicle found: {}", vehicle);

        validVehicle(vehicle, customer);

        var orderService = OrderServiceMapper.toOrderService(orderServiceRequestDTO);
        orderService.addCustomer(customer);
        orderService.addVehicle(vehicle);

        repository.saveAndFlush(orderService);
        log.info("Order service saved: {}", orderService);

        var orderServiceDetail = OrderDetailMapper.toOrderServiceDetail(orderServiceRequestDTO.itens(), orderService);
        log.info("Order service detail saved: {}", orderServiceDetail);

        orderServiceDetailRepository.saveAllAndFlush(orderServiceDetail);
        return OrderServiceMapper.toResponseDTO(orderService);
    }

    private void validVehicle(Vehicle vehicle, Customer customer) {
        if (vehicle.getCustomer().getId().compareTo(customer.getId()) != 0) {
            log.info("Vehicle this license plate {} doesnt belong to customer {}", vehicle.getLicensePlate(), customer.getId());
            throw new VehicleNotBelongToCustomer("Veículo de placa %s não pertence ao cliente %s, verificar novamente."
                    .formatted(vehicle.getLicensePlate(), customer.getName()));
        }
    }

}
