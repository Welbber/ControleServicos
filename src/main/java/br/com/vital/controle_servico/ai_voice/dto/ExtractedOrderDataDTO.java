package br.com.vital.controle_servico.ai_voice.dto;

import java.util.List;

public record ExtractedOrderDataDTO(
        String customerComplaint,
        List<String> suggestedParts,
        List<String> suggestedServices
) {
}
