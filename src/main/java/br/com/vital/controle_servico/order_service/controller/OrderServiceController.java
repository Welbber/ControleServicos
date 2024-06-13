package br.com.vital.controle_servico.order_service.controller;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;
import br.com.vital.controle_servico.order_service.dto.DetailOrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceFilterDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceRequestDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.service.DetailOrderServiceService;
import br.com.vital.controle_servico.order_service.service.FindOrderServiceService;
import br.com.vital.controle_servico.order_service.service.NewOrderServiceService;
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
@RequestMapping("/api/v1/orders-services")
public class OrderServiceController {

    private final NewOrderServiceService newOrderServiceService;
    private final DetailOrderServiceService detailOrderServiceService;
    private final FindOrderServiceService findOrderServiceService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<OrderServiceResponseDTO> createOrder(@PathVariable("customerId") Long customerId, @Valid @RequestBody OrderServiceRequestDTO order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderServiceService.save(customerId, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailOrderServiceResponseDTO> detailOrder(@PathVariable("id") Long orderServiceId) {
        return ResponseEntity.ok().body(detailOrderServiceService.detail(orderServiceId));
    }

    @GetMapping
    public ResponseEntity<Slice<OrderServiceResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(name = "customer", required = false) String customer,
                                                                  @RequestParam(name = "description", required = false) String description,
                                                                  @RequestParam(name = "plate", required = false) String plate,
                                                                  @RequestParam(name = "type", required = false) OrderServiceType type,
                                                                  @RequestParam(name = "status", required = false) OrderServiceStatus status) {
        var filters = OrderServiceFilterDTO.builder()
                .customer(customer)
                .description(description)
                .type(type)
                .status(status)
                .licensePlate(plate)
                .build();
        return new ResponseEntity<>(findOrderServiceService.findAll(filters, PageRequest.of(page, size, Sort.by("description"))), HttpStatus.OK);
    }


}
