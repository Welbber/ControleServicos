package br.com.vital.controle_servico.customers.controller;

import br.com.vital.controle_servico.customers.dto.CustomerFilterDTO;
import br.com.vital.controle_servico.customers.dto.CustomerRequestDTO;
import br.com.vital.controle_servico.customers.dto.CustomerResponseDTO;
import br.com.vital.controle_servico.customers.dto.CustomerStatusResponseDTO;
import br.com.vital.controle_servico.customers.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Slice<CustomerResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(name = "documentNumber", required = false) String documentNumber,
                                                              @RequestParam(name = "name", required = false) String name,
                                                              @RequestParam(name = "email", required = false) String email) {
        var filters = CustomerFilterDTO.builder()
                .name(name)
                .email(email)
                .documentNumber(documentNumber)
                .build();
        return new ResponseEntity<>(service.findAll(filters, PageRequest.of(page, size, Sort.by("name"))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(customerRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.ok().body(service.update(id, customerRequestDTO));
    }

    @PatchMapping("{id}/active")
    public ResponseEntity<CustomerStatusResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.activeCustomer(id, Boolean.TRUE));
    }

    @PatchMapping("{id}/inactive")
    public ResponseEntity<CustomerStatusResponseDTO> inactive(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.inactiveCustomer(id, Boolean.FALSE));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

}