package br.com.vital.controleServico.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Servicos")
@Getter
@Setter
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "Descricao_Geral")
    private String descricaoGeral;


    @Enumerated(EnumType.STRING)
    private StatusServico status;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<Item> itens;

    @Setter(AccessLevel.PRIVATE)
    private Date dataCadastro;

    public Servico(String descricaoGeral, StatusServico status, Veiculo veiculo, List<Item> itens) {
        this.descricaoGeral = descricaoGeral;
        this.status = status;
        this.veiculo = veiculo;
        this.itens = itens;
        this.dataCadastro = new Date();
    }
}