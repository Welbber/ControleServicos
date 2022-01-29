package br.com.vital.controleServico.service;

import br.com.vital.controleServico.dto.ClienteDTO;
import br.com.vital.controleServico.dto.VeiculoDTO;
import br.com.vital.controleServico.entities.Cliente;
import br.com.vital.controleServico.entities.Veiculo;
import br.com.vital.controleServico.repositories.VeiculoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModelMapper model;

    public VeiculoDTO save(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = model.map(veiculoDTO, Veiculo.class);
        veiculo.setCliente(new Cliente(veiculoDTO.getIdCliente()));
        veiculo = veiculoRepository.save(veiculo);
        return model.map(veiculo, VeiculoDTO.class);
    }

    public Page<VeiculoDTO> findAll(Pageable pageable) {
        Page<Veiculo> result = veiculoRepository.findAll(pageable);
        return result.map(v -> model.map(v, VeiculoDTO.class));
    }

    public VeiculoDTO findById(Long id) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        return model.map(veiculo.get(), VeiculoDTO.class);
    }


}