package br.com.vital.controleServico.customers.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Builder
@Getter
@Entity
@DynamicUpdate
@Table(name = "customers")
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "document_number")
    private String documentNumber;

    @EqualsExclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "customer_id")
    private Address address;

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


    public Customer(Long id) {
        this.id = id;
    }

    public Customer merge(Customer customer) {
        this.name = customer.name;
        this.documentNumber = customer.documentNumber;
        this.email = customer.email;
        this.phoneNumber = customer.phoneNumber;
        this.updatedAt = ZonedDateTime.now();
        this.address = customer.address;
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