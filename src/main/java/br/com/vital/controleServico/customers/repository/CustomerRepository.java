package br.com.vital.controleServico.customers.repository;

import br.com.vital.controleServico.customers.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CustomerRepository extends Repository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);


}
