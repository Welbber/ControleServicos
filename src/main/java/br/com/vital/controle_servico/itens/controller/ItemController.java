package br.com.vital.controle_servico.itens.controller;

import br.com.vital.controle_servico.itens.dto.ItemFilterDTO;
import br.com.vital.controle_servico.itens.dto.ItemRequestDTO;
import br.com.vital.controle_servico.itens.dto.ItemResponseDTO;
import br.com.vital.controle_servico.itens.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Slice<ItemResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(name = "description", required = false) String description,
                                                          @RequestParam(name = "code", required = false) String code,
                                                          @RequestParam(name = "type", required = false) String type) {
        var filter = ItemFilterDTO.builder()
                .code(code)
                .description(description)
                .type(type)
                .build();
        return ResponseEntity.ok().body(itemService.findAll(filter, PageRequest.of(page, size, Sort.by("description"))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(itemService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponseDTO> create(@RequestBody @Valid ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(itemRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> update(@PathVariable("id") Long id, ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.ok().body(itemService.update(id, itemRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(itemService.delete(id));
    }

}
