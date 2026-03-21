package br.com.vital.controle_servico.itens.repository;

import br.com.vital.controle_servico.itens.domain.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface ItemRepository extends Repository<Item, UUID> {

    @Transactional
    Item saveAndFlush(Item item);

    @Transactional(readOnly = true)
    Optional<Item> findById(UUID id);

    @Transactional(readOnly = true)
    @Query("""
            select count(i.id) > 1
            from Item i
            where LOWER(i.code) = :code and LOWER(i.description) = :description
            """)
    boolean existByCodeAndDescription(@Param("code") String code, @Param("description") String description);

    @Transactional
    void deleteById(UUID id);

}
