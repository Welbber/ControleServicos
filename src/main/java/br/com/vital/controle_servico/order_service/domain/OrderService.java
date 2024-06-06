package br.com.vital.controle_servico.order_service.domain;

import br.com.vital.controle_servico.customers.domain.Customer;
import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Builder
@Getter
@Entity
@Table(name = "order_service")
@DynamicUpdate
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_service_type")
    private OrderServiceType type;

    @Enumerated(EnumType.STRING)
    private OrderServiceStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private BigDecimal amount;

    @Column(name = "km_vehicle_at")
    private String kmVehicleAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

}
