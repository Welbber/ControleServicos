package br.com.vital.controleServico.customers.service;

import br.com.vital.controleServico.customers.dto.CustomerDTO;
import br.com.vital.controleServico.customers.mapper.CustomerMapper;
import br.com.vital.controleServico.customers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        log.info("Find all customers pageable: {}", pageable);
        var customers = repository.findAll(pageable);
        if (customers.isEmpty()) {
            log.info("Customer list is empty");
            return new PageImpl<>(List.of());
        }

        log.info("Customer list found {}", customers.getContent());
        return customers.map(CustomerMapper::toCustomerDTO);
    }

}