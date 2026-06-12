package br.com.vital.controle_servico.customers.domain;

import br.com.vital.controle_servico.tenants.config.TenantEntityListener;
import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Entity
@DynamicUpdate
@Table(name = "customers")
@EntityListeners(TenantEntityListener.class)
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = java.util.UUID.class)})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;

    private String name;

    @Column(name = "document_number")
    private String documentNumber;

    @EqualsExclude
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setCustomer(this);
        }
    }

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Boolean active;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;


    @EqualsExclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private List<Vehicle> vehicles;

    public Customer(UUID id) {
        this.id = id;
    }

    public Customer merge(Customer customer) {
        this.name = customer.name;
        this.email = customer.email;
        this.phoneNumber = customer.phoneNumber;
        this.updatedAt = ZonedDateTime.now();
        
        if (customer.address != null) {
            if (this.address == null) {
                this.setAddress(customer.address);
            } else {
                this.address.merge(customer.address);
            }
        }
        
        return this;
    }

    public void active() {
        this.active = true;
        this.updatedAt = ZonedDateTime.now();
    }

    public void inactive() {
        this.active = false;
        this.updatedAt = ZonedDateTime.now();
    }
}