package br.com.vital.controle_servico.ai_voice.controller;

import br.com.vital.controle_servico.ai_voice.dto.ExtractedOrderDataDTO;
import br.com.vital.controle_servico.ai_voice.service.VoiceProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/ai-voice")
@RequiredArgsConstructor
public class VoiceProcessingController {

    private final VoiceProcessingService voiceProcessingService;

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExtractedOrderDataDTO> processAudio(@RequestParam("audio") MultipartFile audioFile) {
        ExtractedOrderDataDTO result = voiceProcessingService.processAudioToOrderData(audioFile);
        return ResponseEntity.ok(result);
    }
}
