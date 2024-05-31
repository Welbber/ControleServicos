package br.com.vital.controleServico.customers.service;

import br.com.vital.controleServico.customers.domain.Cliente;
import br.com.vital.controleServico.customers.dto.ClienteDTO;
import br.com.vital.controleServico.customers.repository.ClienteRepository;
import br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO;
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


    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        Page<Cliente> result = clienteRepository.findAllByAtivo(true, pageable);
        return null;
        //result.map(c -> model.map(c, ClienteDTO.class));
    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(id, true);
        return null;
        // model.map(cliente, ClienteDTO.class);
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
//        Cliente cliente = model.map(clienteDTO, Cliente.class);
        var cliente = clienteRepository.save(new Cliente());
        //return model.map(cliente, ClienteDTO.class);
        return null;
    }

    @Transactional(readOnly = true)
    public ClienteDTO update(ClienteDTO novoRegistro) {
        Optional<Cliente> cliente = clienteRepository.findById(novoRegistro.getId());
//        if (cliente.isPresent()) {
//            model.map(novoRegistro, cliente.get());
//            clienteRepository.save(cliente.get());
//        }
//        return model.map(cliente.get(), ClienteDTO.class);
        return null;
    }

    @Transactional(readOnly = true)
    public ClienteDTO delete(Long id) {
        var cliente = clienteRepository.findById(id);
        if (cliente.isPresent())
            cliente.get().setAtivo(false);
        //return model.map(clienteRepository.save(cliente.get()), ClienteDTO.class);
        return null;
    }

    @Transactional(readOnly = true)
    public Page<ClienteQuantidadeServicosDTO> findClienteQuantidadeServico(Pageable pageable) {
//        return clienteRepository.findClienteQuantidadeServico(pageable);
        return null;
    }

}