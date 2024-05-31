package br.com.vital.controleServico.vehicles.repository;

import br.com.vital.controleServico.vehicles.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}