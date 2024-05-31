package br.com.vital.controleServico.vehicles.domain;

import br.com.vital.controleServico.customers.domain.Cliente;
import br.com.vital.controleServico.entities.Servico;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "veiculos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Veiculo {
    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();
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

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Servico> servico;

    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Veiculo(Long id) {
        this.id = id;
    }
}
