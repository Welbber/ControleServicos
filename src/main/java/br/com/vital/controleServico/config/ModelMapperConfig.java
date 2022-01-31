package br.com.vital.controleServico.config;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.entities.Cliente;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper model = new ModelMapper();
        model.addMappings(new PropertyMap<ClienteDTO, Cliente>(){
            @Override
            protected void configure(){
            }
        });
        model.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return model;
    }
}
