package br.com.vital.controleServico.controller;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll(Pageable pageable) {
        Page<ClienteDTO> list = service.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        ClienteDTO cliente = service.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Cliente cliente) {

        return ResponseEntity.ok(service.save(cliente));

    }

    @PutMapping
    public ClienteDTO updateCliente(@RequestBody ClienteDTO cliente) {
        return service.update(cliente);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok(200);
    }
}
