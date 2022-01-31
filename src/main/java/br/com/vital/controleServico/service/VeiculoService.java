package br.com.vital.controleServico.service;

import br.com.vital.controleServico.dto.VeiculoDTO;
import br.com.vital.controleServico.entities.Veiculo;
import br.com.vital.controleServico.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService extends AbstractService<Veiculo, VeiculoDTO, Long> {

    private VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository repository) {
        super(VeiculoDTO.class, Veiculo.class, repository);
        this.veiculoRepository = repository;
    }

    @Override
    public VeiculoDTO delete(Long aLong) {
        return null;
    }
}