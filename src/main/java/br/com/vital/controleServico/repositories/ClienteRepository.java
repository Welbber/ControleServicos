package br.com.vital.controleServico.repositories;

import br.com.vital.controleServico.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Page<Cliente> findAllByAtivo(Boolean ativo, Pageable pageable);

    public Cliente findByIdAndAtivo(Long id, Boolean ativo);
}
