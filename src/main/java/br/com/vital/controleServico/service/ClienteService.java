package br.com.vital.controleServico.service;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO;
import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService extends AbstractService<Cliente, ClienteDTO, Long> {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        super(ClienteDTO.class, Cliente.class, repository);
        this.clienteRepository = repository;
    }

//    @Override
//    public ClienteDTO delete(Long id) {
//        Optional<Cliente> cliente = repository.findById(id);
//
//        if (cliente.isPresent()) {
//            cliente.get().setAtivo(false);
//            repository.save(cliente.get());
//        }
//        return super.model.map(cliente, ClienteDTO.class);
//    }

    @Transactional(readOnly = true)
    public Page<ClienteQuantidadeServicosDTO> findClienteQuantidadeServico(Pageable pageable) {
        return clienteRepository.findClienteQuantidadeServico(pageable);
    }
}