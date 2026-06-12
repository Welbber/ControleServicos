package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface OrderAiIntegrationService {
    
    OrderServiceResponseDTO createDraftOrderByAudio(UUID customerId, UUID vehicleId, UUID tenantId, MultipartFile audio);
}
