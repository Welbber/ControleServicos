package br.com.vital.controle_servico.order_service.controller;

import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.service.OrderAiIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders-services")
@RequiredArgsConstructor
public class OrderServiceVoiceController {

    private final OrderAiIntegrationService orderAiIntegrationService;

    @PostMapping(value = "/voice-draft", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrderServiceResponseDTO> createDraftOrderByAudio(
            @RequestParam("customerId") UUID customerId,
            @RequestParam("vehicleId") UUID vehicleId,
            @RequestParam("tenantId") UUID tenantId,
            @RequestParam("audio") MultipartFile audio) {

        OrderServiceResponseDTO response = orderAiIntegrationService.createDraftOrderByAudio(customerId, vehicleId, tenantId, audio);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
