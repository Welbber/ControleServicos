package br.com.vital.controle_servico.customers.service;

import br.com.vital.controle_servico.customers.dto.CustomerFilterDTO;
import br.com.vital.controle_servico.customers.dto.CustomerRequestDTO;
import br.com.vital.controle_servico.customers.dto.CustomerResponseDTO;
import br.com.vital.controle_servico.customers.dto.CustomerStatusResponseDTO;
import br.com.vital.controle_servico.customers.exception.CustomerAlreadyExistsException;
import br.com.vital.controle_servico.customers.exception.CustomerNotFoundException;
import br.com.vital.controle_servico.customers.mapper.CustomerMapper;
import br.com.vital.controle_servico.customers.repository.CustomerCriteriaRepository;
import br.com.vital.controle_servico.customers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerCriteriaRepository customerCriteriaRepository;

    @Transactional(readOnly = true)
    public Slice<CustomerResponseDTO> findAll(CustomerFilterDTO filters, Pageable pageable) {
        log.info("Find all customers pageable: {} and filters: {}", pageable, filters);
        var customers = customerCriteriaRepository.findAll(filters, pageable);
        if (customers.isEmpty()) {
            log.info("Customer list is empty");
            return new SliceImpl<>(List.of(), pageable, false);
        }

        log.info("Customer list found {}", customers.getContent());
        return customers.map(CustomerMapper::toCustomerDTO);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO findById(Long id) {
        log.info("Find customer by id: {}", id);
        return repository.findById(id)
                .map(CustomerMapper::toCustomerDTO)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO) {
        log.info("Save received customer to save: {}", customerRequestDTO);
        if (repository.existByDocumentNumberAndEmail(customerRequestDTO.documentNumber(), customerRequestDTO.email())) {
            log.info("Customer already exists with documentNumber: {}, ignore request", customerRequestDTO.documentNumber());
            throw new CustomerAlreadyExistsException("Já existe um cliente com esse documento: %s e e-mail: %s."
                    .formatted(customerRequestDTO.documentNumber(), customerRequestDTO.email()));
        }
        var customer = repository.saveAndFlush(CustomerMapper.toCustomer(customerRequestDTO));
        log.info("Customer saved: {}", customer);
        return CustomerMapper.toCustomerDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO customerRequestDTO) {
        log.info("Update received customer to save: {}", customerRequestDTO);
        repository.findByDocumentNumberAndEmail(customerRequestDTO.documentNumber(), customerRequestDTO.email())
                .ifPresent(customer -> {
                    if (!customer.getDocumentNumber().equalsIgnoreCase(customerRequestDTO.documentNumber()) &&
                            !customer.getEmail().equals(customerRequestDTO.email())
                    ) {
                        log.info("Customer already exists with documentNumber: {}, ignore request", customerRequestDTO.documentNumber());
                        throw new CustomerAlreadyExistsException("Já existe um cliente com esse documento : %s e e-mail %s."
                                .formatted(customerRequestDTO.documentNumber(), customerRequestDTO.email()));
                    }
                });

        var optionalCustomer = repository.findById(id);
        var newCustomer = CustomerMapper.toCustomer(customerRequestDTO);
        var mergedCustomer = optionalCustomer
                .map(oldCustomer -> oldCustomer.merge(newCustomer))
                .orElseThrow(CustomerNotFoundException::new);
        log.info("Customer updated: {}", mergedCustomer);
        repository.saveAndFlush(mergedCustomer);
        return CustomerMapper.toCustomerDTO(mergedCustomer);
    }

    @Transactional
    public Boolean delete(Long id) {
        log.info("Delete received customer by id: {}", id);
        var customer = repository.findById(id)
                .map(CustomerMapper::toCustomerDTO)
                .orElseThrow(CustomerNotFoundException::new);
        repository.deleteById(customer.id());
        return true;
    }

    @Transactional
    public CustomerStatusResponseDTO activeCustomer(Long id, Boolean status) {
        log.info("Active received customer {} status to save: {}", id, status);
        var customer = repository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customer.active();
        repository.saveAndFlush(customer);
        log.info("Customer active: {}", customer);
        return new CustomerStatusResponseDTO("Cliente ativado com sucesso", "active");
    }

    @Transactional
    public CustomerStatusResponseDTO inactiveCustomer(Long id, Boolean status) {
        log.info("Inactive received customer {} status to save: {}", id, status);
        var customer = repository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customer.inactive();
        repository.saveAndFlush(customer);
        log.info("Customer inactive: {}", customer);
        return new CustomerStatusResponseDTO("Cliente desativado com sucesso", "inactive");
    }
}
