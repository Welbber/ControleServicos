package br.com.vital.controleServico.controller;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.dto.ClienteQuantidadeServicosDTO;
import br.com.vital.controleServico.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll(Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/findClienteQuantidadeServico")
    public ResponseEntity<Page<ClienteQuantidadeServicosDTO>> findClienteQuantidadeServico(Pageable pageable) {
        return new ResponseEntity<>(service.findClienteQuantidadeServico(pageable),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ClienteDTO cliente) {

        return ResponseEntity.ok(service.save(cliente));

    }

    @PutMapping
    public ClienteDTO updateCliente(@RequestBody ClienteDTO cliente) {
        return service.update(cliente, cliente.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ClienteDTO delete(@PathVariable long id) {
        return service.delete(id);
    }
}