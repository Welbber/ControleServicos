package br.com.vital.controleServico.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<E, ID extends Serializable> extends JpaRepository<E, ID> {

    public Page<E> findAllByAtivo(boolean ativo, Pageable pageable);

    public Optional<E> findByIdAndAtivo(ID id, boolean ativo);
}
