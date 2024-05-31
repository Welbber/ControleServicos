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

    private static AddressDTO getAddressDTO(Address address) {
        return new AddressDTO(
                address.getStreetName(),
                address.getNumber(),
                address.getComplements(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }

}
