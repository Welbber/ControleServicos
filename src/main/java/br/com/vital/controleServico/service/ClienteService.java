package br.com.vital.controleServico.service;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper model;

    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        Page<Cliente> result = clienteRepository.findAll(pageable);
        return result.map(c -> model.map(c, ClienteDTO.class));
    }

    public ClienteDTO findById(Long id) {
        var cliente = clienteRepository.findById(id);

        if (cliente.isPresent())
            return model.map(cliente.get(), ClienteDTO.class);
        return null;

    }

    public ClienteDTO save(Cliente cliente) {
        return  model.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    public ClienteDTO update(ClienteDTO novoRegistro) {
        Optional<Cliente> cliente = clienteRepository.findById(novoRegistro.getClienteId());
        if (cliente.isPresent()) {
            novoRegistro.update(cliente.get());
            clienteRepository.save(cliente.get());
            return novoRegistro;
        }
        return null;
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}