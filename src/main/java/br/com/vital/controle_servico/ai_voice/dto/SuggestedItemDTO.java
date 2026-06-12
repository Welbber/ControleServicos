package br.com.vital.controle_servico.ai_voice.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = SuggestedItemDTO.Deserializer.class)
public class SuggestedItemDTO {
    
    private String name;
    private Double quantity;
    private String unit;

    public static class Deserializer extends JsonDeserializer<SuggestedItemDTO> {
        @Override
        public SuggestedItemDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            
            // If the AI hallucinates and returns a simple string array like ["coxinho..."]
            if (node.isTextual()) {
                return new SuggestedItemDTO(node.asText(), 1.0, "UN");
            } 
            
            // If the AI successfully returns objects like [{"name": "...", "quantity": 1, "unit": "UN"}]
            if (node.isObject()) {
                String name = node.has("name") ? node.get("name").asText() : null;
                Double quantity = node.has("quantity") && !node.get("quantity").isNull() ? node.get("quantity").asDouble() : 1.0;
                String unit = node.has("unit") && !node.get("unit").isNull() ? node.get("unit").asText() : "UN";
                return new SuggestedItemDTO(name, quantity, unit);
            }
            
            return new SuggestedItemDTO();
        }
    }
}
