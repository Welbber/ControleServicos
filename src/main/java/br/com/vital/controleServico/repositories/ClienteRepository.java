package br.com.vital.controleServico.repositories;

import br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO;
import br.com.vital.controleServico.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends GenericRepository<Cliente, Long> {

    @Query("SELECT NEW br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO(CL.id, CL.nome, CL.telefone, CL.veiculos.size) FROM Cliente AS CL WHERE CL.ativo = true GROUP BY CL.nome, CL.telefone")
    public Page<ClienteQuantidadeServicosDTO> findClienteQuantidadeServico(Pageable pageable);


}
