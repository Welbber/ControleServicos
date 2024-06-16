package br.com.vital.controle_servico.itens.dto;

import br.com.vital.controle_servico.itens.domain.ItemType;
import br.com.vital.controle_servico.itens.domain.MeasurementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record ItemRequestDTO(
        @NotNull(message = "O Campo Código/Referência do produto é obrigatório")
        String code,
        @NotNull(message = "O Campo de descrição do produto é obrigatório")
        String description,
        @NotNull(message = "O Campo tipo de produto é obrigatório")
        ItemType type,
        String provider,
        @NotNull(message = "O Campo valor de custo é obrigatório")
        BigDecimal purchaseAmount,
        @NotNull(message = "O Campo valor de venda é obrigatório")
        BigDecimal saleAmount,
        @NotNull
        @Min(value = 1, message = "Quantidade minima inválida")
        Integer quantity,
        MeasurementType measurementType) {
}
