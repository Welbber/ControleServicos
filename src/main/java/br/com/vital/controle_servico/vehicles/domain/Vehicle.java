package br.com.vital.controle_servico.vehicles.domain;

import br.com.vital.controle_servico.tenants.config.TenantEntityListener;
import br.com.vital.controle_servico.customers.domain.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;


@Builder
@Getter
@Entity
@DynamicUpdate
@EqualsAndHashCode
@Table(name = "vehicles")
@EntityListeners(TenantEntityListener.class)
@org.hibernate.annotations.Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.PRIVATE)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;

    private String brand;

    private String model;

    private String color;

    @Column(name = "license_plate", unique = true)
    private String licensePlate;

    private Integer year;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

}
