package br.com.vital.controle_servico.vehicles.controller;

import br.com.vital.controle_servico.vehicles.dto.VehicleFilterDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleRequestDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleResponseDTO;
import br.com.vital.controle_servico.vehicles.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/vehicles")
public class VehicleController {

    private final VehicleService service;

    @GetMapping
    public ResponseEntity<Slice<VehicleResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(name = "plate", required = false) String plate,
                                                             @RequestParam(name = "brand", required = false) String brand,
                                                             @RequestParam(name = "model", required = false) String model,
                                                             @RequestParam(name = "year", required = false) Integer year) {
        var filters = VehicleFilterDTO.builder()
                .plate(plate)
                .brand(brand)
                .model(model)
                .year(year)
                .build();
        return new ResponseEntity<>(service.findAll(filters, PageRequest.of(page, size, Sort.by("year"))), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<VehicleResponseDTO>> findAll(@PathVariable(name = "id") Long customerId) {
        return new ResponseEntity<>(service.findAllByCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleResponseDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(vehicleRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }
}
