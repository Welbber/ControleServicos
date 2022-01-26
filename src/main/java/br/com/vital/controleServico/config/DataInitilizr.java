package br.com.vital.controleServico.config;

import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.entities.Endereco;
import br.com.vital.controleServico.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataInitilizr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0){

        Endereco endereco = new Endereco("Sem fim","105","Os destemidos" ,"58110-000", "proximo aos vizinhos", "Perdida","pb");
        Cliente cliente = new Cliente("Joao Jose", "(83) 9 1234-5678", endereco, "joaojose@gmail.com");

        repository.save(cliente);
        System.out.println("CADASTRO TESTE REALIZADO COM SUCESSO");
    }
}
