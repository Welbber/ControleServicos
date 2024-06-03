package br.com.vital.controleServico.customers.repository;

import br.com.vital.controleServico.customers.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CustomerRepository extends Repository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Customer saveAndFlush(Customer customer);

    @Query("""
            select count(c.id) > 1
            from Customer c
            where c.documentNumber = :documentNumber and c.email = :email
            """)
    boolean existByDocumentNumberAndEmail(@Param("documentNumber") String documentNumber, @Param("email") String email);

    void deleteById(long id);

}
