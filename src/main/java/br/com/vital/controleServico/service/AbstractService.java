package br.com.vital.controleServico.service;

import br.com.vital.controleServico.entities.AbstractType;
import br.com.vital.controleServico.repositories.GenericRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.Serializable;
import java.util.Optional;

@Service
public abstract class AbstractService<E extends AbstractType, D, ID extends Serializable> {

    protected GenericRepository<E, ID> repository;

    private static final Boolean ATIVO = true;

    @Autowired
    protected ModelMapper model;

    private Class<D> typeDTO;

    private Class<E> typeEntity;


    public AbstractService(Class<D> typeDTO, Class<E> typeEntity, GenericRepository<E, ID> repository) {
        this.typeDTO = typeDTO;
        this.typeEntity = typeEntity;
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<D> findAll(Pageable pageable) {
        Page<E> result = repository.findAllByAtivo(ATIVO, pageable);
        return result.map(c -> model.map(c, typeDTO));
    }

    @Transactional(readOnly = true)
    public D findById(ID id) {
        Optional<E> entity = repository.findByIdAndAtivo(id, ATIVO);
        return model.map(entity.get(), typeDTO);
    }

    @Transactional
    public D save(D entityDTO) {
        E entity = model.map(entityDTO, typeEntity);
        entity = repository.save(entity);
        return model.map(entity, typeDTO);
    }

    @Modifying
    @Transactional
    public D update(D newEntityDTO, ID id) {
        Optional<E> entity = repository.findById(id);
        if (entity.isPresent()) {
            model.map(newEntityDTO, entity.get());
            repository.save(entity.get());
        }
        return model.map(entity.get(), typeDTO);
    }

    @Modifying
    @Transactional
    public D delete(ID id){
        Optional<E> entity = repository.findByIdAndAtivo(id, ATIVO);
        if (entity.isPresent()){
            repository.delete(id);
            return model.map(entity.get(), typeDTO);
        }
        return null;
    };
}