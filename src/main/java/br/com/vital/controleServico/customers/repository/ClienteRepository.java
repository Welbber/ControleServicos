package br.com.vital.controleServico.customers.repository;

import br.com.vital.controleServico.customers.domain.Cliente;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Lock(LockModeType.READ)
    public Page<Cliente> findAllByAtivo(Boolean ativo, Pageable pageable);

    @Lock(LockModeType.READ)
    public Cliente findByIdAndAtivo(Long id, Boolean ativo);

//    @Lock(LockModeType.READ)
    //@Query("SELECT NEW br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO(CL.id, CL.nome, CL.telefone, CL.veiculos.size) FROM Cliente AS CL WHERE CL.ativo = true GROUP BY CL.nome, CL.telefone")
//    public Page<ClienteQuantidadeServicosDTO> findClienteQuantidadeServico(Pageable pageable);
}
