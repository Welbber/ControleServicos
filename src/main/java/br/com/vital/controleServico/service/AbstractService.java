package br.com.vital.controleServico.service;

import br.com.vital.controleServico.repositories.GenericRepositoryImpl;
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
public abstract class AbstractService<E, D, ID extends Serializable> {

    protected GenericRepositoryImpl<E, ID> repositoryImpl;

    @Autowired
    protected ModelMapper model;

    private Class<D> typeDTO;

    private Class<E> typeEntity;

    public AbstractService(Class<D> typeDTO, Class<E> typeEntity, GenericRepositoryImpl<E, ID> repositoryImpl) {
        this.typeDTO = typeDTO;
        this.typeEntity = typeEntity;
        this.repositoryImpl = repositoryImpl;
    }

    @Transactional(readOnly = true)
    public Page<D> findAll(Pageable pageable) {
        Page<E> result = repositoryImpl.findAll(pageable);
        return result.map(c -> model.map(c, typeDTO));
    }

    @Transactional(readOnly = true)
    public D findById(ID id) {
        Optional<E> entity = repositoryImpl.findById(id);
        return model.map(entity.get(), typeDTO);
    }

    @Transactional(readOnly = true)
    @Modifying
    public D save(D entityDTO) {
        E entity = model.map(entityDTO, typeEntity);
        entity = repositoryImpl.save(entity);
        return model.map(entity, typeDTO);
    }

    @Transactional(readOnly = true)
    @Modifying
    public D update(D newEntityDTO, ID id) {
        Optional<E> entity = repositoryImpl.findById(id);
        if (entity.isPresent()) {
            model.map(newEntityDTO, entity.get());
            repositoryImpl.save(entity.get());
        }
        return model.map(entity.get(), typeDTO);
    }

    @Transactional(readOnly = true)
    @Modifying
    public D delete(ID id) {
        Optional<E> entity = repositoryImpl.deleteByIdAndAtivo(id);

        if (entity.isPresent())
            return model.map(entity.get(), typeDTO);
        return null;
    }
}