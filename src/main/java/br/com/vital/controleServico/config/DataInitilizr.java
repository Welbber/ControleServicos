package br.com.vital.controleServico.config;

import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.entities.Endereco;
import br.com.vital.controleServico.entities.Veiculo;
import br.com.vital.controleServico.repositories.ClienteRepository;

import br.com.vital.controleServico.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;



@Component
public class DataInitilizr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0){

        Endereco endereco = new Endereco(null, null,"Sem fim","105","Os destemidos" ,"58110-000", "proximo aos vizinhos", "Perdida","pb");
        Cliente cliente = new Cliente(null, "Joao Jose", "(83) 9 1234-5678", endereco, null, "joaojose@gmail.com", true);

        Veiculo veiculo = new Veiculo();

        veiculo.setModelo("FORD KA");
        veiculo.setPlaca("KGZ-1253");
        veiculo.setKilometgragemInicial(45012);
        veiculo.setKilometgragemFinal(45015);
        veiculo.setObservacoes("");


        veiculoRepository.save(veiculo);

        clienteRepository.save(cliente);
        veiculo.setCliente(new Cliente(1L));

        veiculoRepository.save(veiculo);


        System.out.println("CADASTRO TESTE REALIZADO COM SUCESSO");
    }
}
