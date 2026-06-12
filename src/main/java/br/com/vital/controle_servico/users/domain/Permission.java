package br.com.vital.controle_servico.users.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Builder
@Getter
@Entity
@Table(name = "permissions")
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

}
