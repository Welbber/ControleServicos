package br.com.vital.controleServico.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Servicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String descricaoGeral;


    @Enumerated(EnumType.STRING)
    private StatusServico status = StatusServico.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    @Setter(AccessLevel.PRIVATE)
    private Veiculo veiculo;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<Item> itens;

    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();

    public Servico(Long id){
        this.id = id;
    }
}