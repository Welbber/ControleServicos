package br.com.vital.controle_servico.ai_voice.service;

import br.com.vital.controle_servico.ai_voice.dto.ExtractedOrderDataDTO;
import org.springframework.web.multipart.MultipartFile;

public interface VoiceProcessingService {

    ExtractedOrderDataDTO processAudioToOrderData(MultipartFile audioFile);
}
