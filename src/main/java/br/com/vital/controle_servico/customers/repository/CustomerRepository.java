package br.com.vital.controle_servico.customers.repository;

import br.com.vital.controle_servico.customers.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface CustomerRepository extends Repository<Customer, UUID> {

    @Transactional(readOnly = true)
    Optional<Customer> findById(UUID id);

    @Transactional
    Customer saveAndFlush(Customer customer);

    @Transactional(readOnly = true)
    @Query("""
            select count(c.id) > 1
            from Customer c
            where c.documentNumber = :documentNumber and c.email = :email
            """)
    boolean existByDocumentNumberAndEmail(@Param("documentNumber") String documentNumber, @Param("email") String email);

    @Transactional
    void deleteById(UUID id);

    @Transactional(readOnly = true)
    Optional<Customer> findByDocumentNumberAndEmail(String documentNumber, String email);
    
}
