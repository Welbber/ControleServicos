package br.com.vital.controleServico.entities;


import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractType {
    protected Boolean ativo = true;
}
