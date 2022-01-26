package br.com.vital.controleServico.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Clientes")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotBlank(message = "Nome Cliente não pode ser nulo")
    private String nome;

    private String telefone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos;

    private String email;

    @Setter(AccessLevel.PRIVATE)
    private Date dataCadastro = new Date();

    public Cliente(String nome, String telefone, Endereco endereco, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.veiculos = new ArrayList<Veiculo>();
        this.email = email;
        //this.dataCadastro = new Date();
    }

    public void adicionaVeiculo(@NotBlank Veiculo veiculo) {
        this.veiculos.add(veiculo);
    }
}
