package br.com.vital.controleServico.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Enderecos")
@Getter
@Setter
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @OneToOne(mappedBy = "endereco")
    private Cliente cliente;

    private String rua;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    private String cidade;

    private String uf;

    @Setter(AccessLevel.PRIVATE)
    private Date dataCadastro;

    public Endereco(String rua, String numero, String bairro, String cep, String complemento, String cidade, String uf) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.cidade = cidade;
        this.uf = uf;
        this.dataCadastro = new Date();
    }
}
