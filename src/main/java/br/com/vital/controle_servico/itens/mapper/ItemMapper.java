package br.com.vital.controle_servico.itens.mapper;

import br.com.vital.controle_servico.itens.domain.Item;
import br.com.vital.controle_servico.itens.dto.ItemRequestDTO;
import br.com.vital.controle_servico.itens.dto.ItemResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    public static ItemResponseDTO toDTO(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getCode(),
                item.getDescription(),
                item.getType(),
                item.getSaleAmount(),
                item.getQuantity());
    }

    public static Item toItem(ItemRequestDTO itemRequestDTO) {
        return Item.builder()
                .code(itemRequestDTO.code())
                .description(itemRequestDTO.description())
                .provider(itemRequestDTO.provider())
                .quantity(itemRequestDTO.quantity())
                .type(itemRequestDTO.type())
                .saleAmount(itemRequestDTO.saleAmount())
                .purchaseAmount(itemRequestDTO.purchaseAmount())
                .measurementType(itemRequestDTO.measurementType())
                .build();
    }
}


