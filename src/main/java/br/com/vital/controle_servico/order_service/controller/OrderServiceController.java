package br.com.vital.controle_servico.order_service.controller;

import br.com.vital.controle_servico.order_service.dto.OrderServiceDTO;
import br.com.vital.controle_servico.order_service.service.OrderServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders-services")
public class OrderServiceController {

    private final OrderServiceService service;

    @PostMapping
    public ResponseEntity<OrderServiceDTO> createOrder(@RequestBody OrderServiceDTO order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(order));
    }

}
