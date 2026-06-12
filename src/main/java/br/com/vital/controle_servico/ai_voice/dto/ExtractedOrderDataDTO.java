package br.com.vital.controle_servico.ai_voice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtractedOrderDataDTO {
    private String customerComplaint;
    private List<SuggestedItemDTO> suggestedParts;
    private List<SuggestedItemDTO> suggestedServices;
}
