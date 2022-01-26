package br.com.vital.controleServico.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "Itens")
@Getter
@Setter
@NoArgsConstructor
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
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    public Item(String descricao, Double valor, TipoItem tipo, Servico servico) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.dataCadastro = new Date();
        this.servico = servico;
    }
}
