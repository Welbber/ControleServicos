package br.com.vital.controle_servico.customers.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Entity
@Table(name = "address")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String street;

    private Integer number;

    private String district;

    @Column(name = "zip_code")
    private String zipCode;

    private String complements;

    private String city;

    private String state;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}