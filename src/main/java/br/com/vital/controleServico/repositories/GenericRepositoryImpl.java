package br.com.vital.controleServico.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.io.Serializable;
import java.util.Optional;


public class GenericRepositoryImpl<E, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements GenericRepository<E, ID> {

    private final EntityManager entityManager;

    @Autowired
    public GenericRepositoryImpl(Class<E> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<E> findById(ID id) {

        Query query = entityManager.createQuery("SELECT e FROM " + this.getDomainClass().getSimpleName() + " e WHERE e.ativo = ?1").setParameter(1, true);
        E entity = entityManager.find(this.getDomainClass(), id);
        Optional<E> returned = Optional.empty();
        if (entity != null) {
            returned = Optional.of(entity);
        }
        entityManager.flush();
        return returned;
    }

    public Optional<E> deleteByIdAndAtivo(ID id) {
        E deleted = entityManager.find(this.getDomainClass(), id);
        Optional<E> returned = Optional.empty();
        if (deleted != null) {
            entityManager.createQuery("UPDATE " + this.getDomainClass().getSimpleName() + " e SET e.ativo = false WHERE e.id = ?1")
                    .setParameter(1, id).executeUpdate();
        }
        entityManager.flush();
        return returned;
    }
}
