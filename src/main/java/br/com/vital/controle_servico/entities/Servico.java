package br.com.vital.controle_servico.entities;

import br.com.vital.controle_servico.vehicles.domain.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Servicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servico {

    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();
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
    private Vehicle vehicle;
//    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
//    private List<Item> itens;

    public Servico(Long id) {
        this.id = id;
    }
}