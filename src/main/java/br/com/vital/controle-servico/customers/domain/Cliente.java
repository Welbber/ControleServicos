package br.com.vital.controleServico.customers.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @NotBlank(message = "Nome Cliente não pode ser nulo")
    private String nome;
    private String telefone;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private br.com.vital.controleServico.customers.domain.Endereco endereco;


    private String email;
    private Boolean ativo = true;

    public Cliente(Long id) {
        this.id = id;
    }
}