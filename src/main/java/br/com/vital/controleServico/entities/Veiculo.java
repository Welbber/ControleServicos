package br.com.vital.controleServico.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Veiculos")
@Getter
@Setter
@NoArgsConstructor
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String modelo;

    @NotBlank(message = "Placa do carro não pode ser vazio")
    private String placa;

    @Min(0)
    @Column(name = "Kilometragem_Inicial")
    private Integer kilometgragemInicial;

    @Min(0)
    @Column(name = "Kilometragem_Final")
    private Integer kilometgragemFinal;

    private String observacoes;

    @Setter(AccessLevel.PRIVATE)
    private Date dataCadastro;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Servico> servico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Veiculo(String modelo, String placa, Integer kilometgragemInicial, String observacoes, Cliente cliente) {
        this.modelo = modelo;
        this.placa = placa;
        this.kilometgragemInicial = kilometgragemInicial;
        this.kilometgragemFinal = null;
        this.observacoes = observacoes;
        this.dataCadastro = new Date();
        this.cliente = cliente;
    }
}
