package br.com.vital.controleServico.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Itens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @NotBlank(message = "Descrição do Item não pode ser vazia")
    private String descricao;
    @NotBlank(message = "Valor do Item não pode ser vazio")
    private Double valor;
    @NotBlank(message = "Tipo Item não pode ser vazio")
    @Enumerated(EnumType.STRING)
    private TipoItem tipo;
    @ManyToOne
    @JoinColumn(name = "servico_id")
    @Setter(AccessLevel.PRIVATE)
    private Servico servico;
}