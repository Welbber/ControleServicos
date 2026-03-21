package br.com.vital.controle_servico.ai_voice.service.impl;

import br.com.vital.controle_servico.ai_voice.dto.ExtractedOrderDataDTO;
import br.com.vital.controle_servico.ai_voice.exception.OpenAiIntegrationException;
import br.com.vital.controle_servico.ai_voice.service.VoiceProcessingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VoiceProcessingServiceImpl implements VoiceProcessingService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final ObjectMapper objectMapper;
    private final RestClient restClient = RestClient.create();

    private static final String WHISPER_API_URL = "https://api.openai.com/v1/audio/transcriptions";
    private static final String CHAT_API_URL = "https://api.openai.com/v1/chat/completions";

    private static final String SYSTEM_PROMPT = """
            You are the expert AI assistant, a Brazilian auto repair shop.
            Analyze the mechanic's audio transcription and extract the data into a valid JSON matching this exact structure:
            {
              "customerComplaint": "string",
              "suggestedParts": ["string"],
              "suggestedServices": ["string"]
            }
            CRITICAL INSTRUCTION: The transcription may contain phonetic errors due to workshop background noise. 
            Use your automotive expertise to correct them before generating the JSON. 
            For example, if you read "limpeza dos micos" or "mico injetor", strictly correct it to "limpeza de bicos" or "bicos injetores".
            Ensure all extracted text is in natural Brazilian Portuguese (PT-BR).
            Return ONLY the JSON object, without Markdown formatting.
            """;

    @Override
    public ExtractedOrderDataDTO processAudioToOrderData(MultipartFile audioFile) {
        try {
            String transcription = transcribeAudio(audioFile);
            return extractStructuredData(transcription);
        } catch (org.springframework.web.client.RestClientResponseException e) {
            throw new OpenAiIntegrationException("OpenAI API Error: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new OpenAiIntegrationException("Failed to process audio file with OpenAI: " + e.getMessage(), e);
        }
    }

    private String transcribeAudio(MultipartFile audioFile) throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", "whisper-1");
        
        body.add("file", audioFile.getResource());

        body.add("prompt", "oficina mecânica, carro, motor, bicos injetores, velas, suspensão, freio, embreagem, correia dentada, scanner, troca de óleo, cabeçote");

        String response = restClient.post()
                .uri(WHISPER_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(String.class);
                
        JsonNode rootNode = objectMapper.readTree(response);
        return rootNode.path("text").asText();
    }

    private ExtractedOrderDataDTO extractStructuredData(String transcription) throws Exception {
        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", transcription)
                ),
                "temperature", 0.0,
                "response_format", Map.of("type", "json_object")
        );

        String response = restClient.post()
                .uri(CHAT_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);
                
        JsonNode rootNode = objectMapper.readTree(response);
        String functionContent = rootNode.path("choices").get(0).path("message").path("content").asText();
        
        return objectMapper.readValue(functionContent, ExtractedOrderDataDTO.class);
    }
}
