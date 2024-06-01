package br.com.vital.controleServico.vehicles.controller;

import br.com.vital.controleServico.vehicles.dto.VehicleDTO;
import br.com.vital.controleServico.vehicles.dto.VehicleFilterDTO;
import br.com.vital.controleServico.vehicles.service.VehicleService;
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
@RequestMapping(value = "/api/v1/vehicles")
public class VehicleController {

    private final VehicleService service;

    @GetMapping
    public ResponseEntity<Slice<VehicleDTO>> findAll(@RequestParam(defaultValue = "0") int page,
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(vehicleDTO));
    }

}
