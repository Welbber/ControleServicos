package br.com.vital.controleServico.controller;

import br.com.vital.controleServico.dto.VeiculoDTO;
import br.com.vital.controleServico.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @GetMapping
    public ResponseEntity<Page<VeiculoDTO>> findAll(Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody VeiculoDTO veiculo) {

        return ResponseEntity.ok(service.save(veiculo));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody VeiculoDTO veiculoDTO) {
        return ResponseEntity.ok(service.update(veiculoDTO, veiculoDTO.getId()));
    }
}
