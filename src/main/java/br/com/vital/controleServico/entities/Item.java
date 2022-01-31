package br.com.vital.controleServico.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Itens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
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

    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "servico_id")
    @Setter(AccessLevel.PRIVATE)
    private Servico servico;
}