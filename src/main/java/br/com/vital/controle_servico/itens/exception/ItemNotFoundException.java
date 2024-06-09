package br.com.vital.controle_servico.itens.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Não foi possível localizar o Item pelo identificador");
    }

}
