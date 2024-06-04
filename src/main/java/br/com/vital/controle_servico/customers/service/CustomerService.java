package br.com.vital.controle_servico.customers.service;

import br.com.vital.controle_servico.common.exception.CustomerAlreadyExistsException;
import br.com.vital.controle_servico.customers.dto.CustomerDTO;
import br.com.vital.controle_servico.customers.dto.CustomerFilterDTO;
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
    public Slice<CustomerDTO> findAll(CustomerFilterDTO filters, Pageable pageable) {
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
    public CustomerDTO findById(Long id) {
        log.info("Find customer by id: {}", id);
        return repository.findById(id)
                .map(CustomerMapper::toCustomerDTO)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.info("Save received customer to save: {}", customerDTO);
        if (repository.existByDocumentNumberAndEmail(customerDTO.documentNumber(), customerDTO.email())) {
            log.info("Customer already exists with documentNumber: {}, ignore request", customerDTO.documentNumber());
            throw new CustomerAlreadyExistsException("Customer already exists with documentNumber: %s and email %s."
                    .formatted(customerDTO.documentNumber(), customerDTO.email()));
        }
        var customer = repository.saveAndFlush(CustomerMapper.toCustomer(customerDTO));
        log.info("Customer saved: {}", customer);
        return CustomerMapper.toCustomerDTO(customer);
    }

    @Transactional
    public CustomerDTO update(CustomerDTO customerDTO) {
        //TODO: Implementar regra de validação para saber se existe outro email
        log.info("Update received customer to save: {}", customerDTO);
        var optionalCustomer = repository.findById(customerDTO.id());
        var newCustomer = CustomerMapper.toCustomer(customerDTO);
        var mergedCustomer = optionalCustomer
                .map(oldCustomer -> oldCustomer.merge(newCustomer))
                .orElseThrow(CustomerNotFoundException::new);
        log.info("Customer updated: {}", mergedCustomer);
        repository.saveAndFlush(mergedCustomer);
        return customerDTO;
    }

    @Transactional
    public Boolean delete(Long id) {
        log.info("Delete received customer by id: {}", id);
        //TODO: implementar validação para saber se exsite um registro com id passado
        repository.deleteById(id);
        return true;
    }

    @Transactional
    public Boolean activeCustomer(Long id, Boolean status) {
        log.info("Active received customer {} status to save: {}", id, status);
        var customer = repository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customer.active();
        repository.saveAndFlush(customer);
        log.info("Customer active: {}", customer);
        return true;
    }

    @Transactional
    public Boolean inactiveCustomer(Long id, Boolean status) {
        log.info("Inactive received customer {} status to save: {}", id, status);
        var customer = repository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customer.inactive();
        repository.saveAndFlush(customer);
        log.info("Customer inactive: {}", customer);
        return true;
    }
}
