package br.com.vital.controle_servico.order_service.domain;

import br.com.vital.controle_servico.itens.domain.Item;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Builder
@Getter
@DynamicUpdate
@EqualsAndHashCode
@Entity
@Table(name = "order_service_detail_itens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_service_id", referencedColumnName = "id")
    private OrderService orderService;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "quantity_item")
    private Integer quantityItens;

    @Column(name = "sale_amount_at")
    private BigDecimal saleAmountAt;

}
