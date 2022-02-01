package br.com.vital.controleServico.repositories;

import br.com.vital.controleServico.entities.AbstractType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<E extends AbstractType, ID extends Serializable> extends JpaRepository<E, ID> {

    public Page<E> findAllByAtivo(boolean ativo, Pageable pageable);

    public Optional<E> findByIdAndAtivo(ID id, boolean ativo);

    @Query("update #{#entityName} e set e.ativo=false where e.id=?1")
    @Modifying
    public void delete(ID id);
}
