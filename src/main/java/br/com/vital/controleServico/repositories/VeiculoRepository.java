package br.com.vital.controleServico.repositories;

import br.com.vital.controleServico.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}