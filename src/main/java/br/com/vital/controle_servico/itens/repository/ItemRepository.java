package br.com.vital.controle_servico.itens.repository;

import br.com.vital.controle_servico.itens.domain.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ItemRepository extends Repository<Item, Long> {

    @Transactional
    Item saveAndFlush(Item item);

    @Transactional(readOnly = true)
    Optional<Item> findById(Long id);

    @Transactional(readOnly = true)
    @Query("""
            select count(i.id) > 1
            from Item i
            where i.code = :code and i.description = :description
            """)
    boolean existByCodeAndDescription(@Param("code") String code, @Param("description") String description);

    @Transactional
    void deleteById(Long id);

}
