package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.order_service.dto.OrderServiceFilterDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindOrderServiceService {

    @Transactional
    public Slice<OrderServiceResponseDTO> findAll(OrderServiceFilterDTO filter, PageRequest pageRequest) {
        log.info("Find OrderService by filters {} and page: {}", filter, pageRequest);
        return null;
    }

}
