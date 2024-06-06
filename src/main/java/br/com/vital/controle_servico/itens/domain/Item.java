package br.com.vital.controle_servico.itens.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Builder
@Entity
@Table(name = "itens")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private TipoItem type;

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

}