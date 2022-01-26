package br.com.vital.controleServico.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper model = new ModelMapper();

        model.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return model;
    }
}
