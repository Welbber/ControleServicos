package br.com.vital.controleServico.customers.controller;

import br.com.vital.controleServico.customers.dto.CustomerDTO;
import br.com.vital.controleServico.customers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(service.findAll(PageRequest.of(page, size)), HttpStatus.OK);
    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity findById(@PathVariable long id) {
//        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/findClienteQuantidadeServico")
//    public ResponseEntity<Page<ClienteQuantidadeServicosDTO>> findClienteQuantidadeServico(Pageable pageable) {
//        return new ResponseEntity<>(service.findClienteQuantidadeServico(pageable), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity create(@RequestBody CustomerDTO cliente) {
//
//        return ResponseEntity.ok(service.save(cliente));
//
//    }
//
//    @PutMapping
//    public CustomerDTO updateCliente(@RequestBody CustomerDTO cliente) {
//        return service.update(cliente);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public CustomerDTO delete(@PathVariable long id) {
//        return service.delete(id);
//    }
}