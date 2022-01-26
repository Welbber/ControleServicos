package br.com.vital.controleServico.dto;

import br.com.vital.controleServico.entities.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClienteDTO {

    private Long clienteId;

    private String nome;

    private String telefone;

    private String email;

    private String rua;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    private String cidade;

    private String uf;

    private Date dataCadastro;

    public void update(Cliente cliente){
        cliente.setNome(getNome());
        cliente.setEmail(getEmail());
        cliente.setTelefone(getTelefone());
        cliente.getEndereco().setRua(getRua());
        cliente.getEndereco().setNumero(getNumero());
        cliente.getEndereco().setBairro(getBairro());
        cliente.getEndereco().setCep(getCep());
        cliente.getEndereco().setComplemento(getComplemento());
        cliente.getEndereco().setCidade(getCidade());
        cliente.getEndereco().setUf(getUf());
    }
}