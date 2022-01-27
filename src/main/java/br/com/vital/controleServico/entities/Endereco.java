package br.com.vital.controleServico.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Enderecos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @OneToOne(mappedBy = "endereco")
    @Setter(AccessLevel.PRIVATE)
    private Cliente cliente;

    private String rua;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    private String cidade;

    private String uf;

    @Setter(AccessLevel.PRIVATE)
    private final LocalDate dataCadastro = LocalDate.now();
}