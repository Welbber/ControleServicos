package br.com.vital.controle_servico.itens.repository;

import br.com.vital.controle_servico.itens.domain.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ItemRepository extends Repository<Item, Long> {

    Item saveAndFlush(Item item);

    Optional<Item> findById(Long id);

    @Query("""
            select count(i.id) > 1
            from Item i
            where i.code = :code and i.description = :description
            """)
    boolean existByCodeAndDescription(@Param("code") String code, @Param("description") String description);

    void deleteById(Long id);
    
}
