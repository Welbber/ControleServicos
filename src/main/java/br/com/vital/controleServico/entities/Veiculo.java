package br.com.vital.controleServico.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Veiculos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String modelo;

    @NotBlank(message = "Placa do carro não pode ser vazio")
    private String placa;

    @Min(0)
    private Integer kilometgragemInicial;

    @Min(0)
    private Integer kilometgragemFinal;

    private String observacoes;

    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Servico> servico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @Setter(AccessLevel.PRIVATE)
    private Cliente cliente;
}
