package br.com.vital.controleServico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository<E, ID extends Serializable> extends Repository<E, ID>, JpaRepository<E, ID> {

}
