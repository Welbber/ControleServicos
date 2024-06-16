package br.com.vital.controle_servico.order_service.service;

import br.com.vital.controle_servico.order_service.dto.DetailOrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceFilterDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.exception.OrderServiceNotFoundException;
import br.com.vital.controle_servico.order_service.mapper.OrderServiceMapper;
import br.com.vital.controle_servico.order_service.repository.OrderServiceCriteriaRepository;
import br.com.vital.controle_servico.order_service.repository.OrderServiceDetailCustomerRepository;
import br.com.vital.controle_servico.order_service.util.BuildPDFDetailOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailOrderServiceService {

    private final OrderServiceDetailCustomerRepository orderServiceDetailFindRepository;
    private final OrderServiceCriteriaRepository criteriaRepository;


    @Transactional(readOnly = true)
    public DetailOrderServiceResponseDTO detail(Long orderServiceId) {
        log.info("Consult received to detail for order with id: {}", orderServiceId);
        var detail = orderServiceDetailFindRepository.findByOrderServiceId(orderServiceId);
        if (Objects.isNull(detail)) {
            log.warn("OrderService detail is empty");
            throw new OrderServiceNotFoundException();
        }
        log.info("Result received to detail for order with id: {}", detail);
        return detail;
    }

    @Transactional(readOnly = true)
    public Slice<OrderServiceResponseDTO> findAll(OrderServiceFilterDTO filter, PageRequest pageRequest) {
        log.info("Find OrderService by filters {} and page: {}", filter, pageRequest);
        var orders = criteriaRepository.findAll(filter, pageRequest);
        if (orders.isEmpty()) {
            log.info("OrderService list is empty");
            return new SliceImpl<>(java.util.List.of(), pageRequest, false);
        }
        log.info("OrderService list found {}", orders.getContent());
        return orders.map(OrderServiceMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ByteArrayOutputStream exportPdf(Long orderServiceId) {
        log.info("Consult received to detail for order with id: {} to export PDF", orderServiceId);
        var detail = orderServiceDetailFindRepository.findByOrderServiceId(orderServiceId);
        if (Objects.isNull(detail)) {
            throw new OrderServiceNotFoundException();
        }
        return BuildPDFDetailOrderService.buildPDF(detail);
    }

}
