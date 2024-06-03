package br.com.vital.controleServico.customers.mapper;

import br.com.vital.controleServico.customers.domain.Address;
import br.com.vital.controleServico.customers.domain.Customer;
import br.com.vital.controleServico.customers.dto.AddressDTO;
import br.com.vital.controleServico.customers.dto.CustomerDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {

    public static CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getDocumentNumber(),
                customer.getPhoneNumber(),
                getAddressDTO(customer.getAddress()));
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.id())
                .name(customerDTO.name())
                .email(customerDTO.email())
                .documentNumber(customerDTO.documentNumber())
                .phoneNumber(customerDTO.phoneNumber())
                .address(toAddress(customerDTO.address()))
                .build();
    }

    private static Address toAddress(AddressDTO address) {
        return Address.builder()
                .id(address.id())
                .city(address.city())
                .complements(address.complement())
                .state(address.state())
                .zipCode(address.zipCode())
                .number(address.number())
                .streetName(address.street())
                .district(address.district())
                .build();
    }

    private static AddressDTO getAddressDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreetName(),
                address.getNumber(),
                address.getComplements(),
                address.getCity(),
                address.getState(),
                address.getDistrict(),
                address.getZipCode()
        );
    }

}
