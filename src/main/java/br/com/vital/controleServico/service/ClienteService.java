package br.com.vital.controleServico.service;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO;
import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.repositories.ClienteRepository;
import br.com.vital.controleServico.repositories.GenericRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class ClienteService extends AbstractService<Cliente, ClienteDTO, Long> {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService(EntityManager entityManager) {
        super(ClienteDTO.class, Cliente.class, new GenericRepositoryImpl<Cliente, Long>(Cliente.class, entityManager));
    }

    @Transactional(readOnly = true)
    public Page<ClienteQuantidadeServicosDTO> findClienteQuantidadeServico(Pageable pageable) {
        return clienteRepository.findClienteQuantidadeServico(pageable);
    }
}