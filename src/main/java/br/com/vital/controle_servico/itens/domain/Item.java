package br.com.vital.controle_servico.itens.domain;

import br.com.vital.controle_servico.itens.dto.ItemRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@ToString
@Builder
@Entity
@Table(name = "itens")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    private String provider;//Fornecedor

    @Column(name = "purchase_amount")
    private BigDecimal purchaseAmount;

    @Column(name = "sale_amount")
    private BigDecimal saleAmount;

    @Column(name = "unit_stock")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_measurement")
    private MeasurementType measurementType;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    private Boolean active;

    public Item(Long id) {
        this.id = id;
    }

    public void merger(ItemRequestDTO itemRequestDTO) {
        this.code = itemRequestDTO.code();
        this.description = itemRequestDTO.description();
        this.type = itemRequestDTO.type();
        this.provider = itemRequestDTO.provider();
        this.purchaseAmount = itemRequestDTO.purchaseAmount();
        this.saleAmount = itemRequestDTO.saleAmount();
        this.quantity = itemRequestDTO.quantity();
        this.measurementType = itemRequestDTO.measurementType();
        this.updatedAt = ZonedDateTime.now();
    }

}