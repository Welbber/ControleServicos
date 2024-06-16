package br.com.vital.controle_servico.order_service.mapper;

import br.com.vital.controle_servico.itens.domain.Item;
import br.com.vital.controle_servico.itens.dto.ItemOrderRequestDTO;
import br.com.vital.controle_servico.order_service.domain.OrderService;
import br.com.vital.controle_servico.order_service.domain.OrderServiceDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDetailMapper {

    public static List<OrderServiceDetail> toOrderServiceDetail(List<ItemOrderRequestDTO> itemOrderRequestDTO, OrderService orderService) {
        return itemOrderRequestDTO.stream()
                .map(item ->
                        OrderServiceDetail.builder()
                                .orderService(orderService)
                                .item(new Item(item.id()))
                                .saleAmountAt(item.saleAmount())
                                .quantityItens(item.quantity())
                                .build())
                .toList();
    }


}
