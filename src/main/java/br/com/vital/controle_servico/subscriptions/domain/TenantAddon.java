package br.com.vital.controle_servico.subscriptions.domain;

import br.com.vital.controle_servico.tenants.domain.Tenant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenant_addons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantAddon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Feature feature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
