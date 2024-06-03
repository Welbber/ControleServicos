package br.com.vital.controleServico.vehicles.service;

import br.com.vital.controleServico.customers.domain.Customer;
import br.com.vital.controleServico.vehicles.domain.Veiculo;
import br.com.vital.controleServico.vehicles.dto.VeiculoDTO;
import br.com.vital.controleServico.vehicles.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public VeiculoDTO save(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo(); //model.map(veiculoDTO, Veiculo.class);
        veiculo.setCustomer(new Customer(veiculoDTO.getIdCliente()));
        veiculo = veiculoRepository.save(veiculo);
//        return model.map(veiculo, VeiculoDTO.class);
        return null;
    }

    public Page<VeiculoDTO> findAll(Pageable pageable) {
        Page<Veiculo> result = veiculoRepository.findAll(pageable);
        return null;//result.map(v -> model.map(v, VeiculoDTO.class));
    }
}