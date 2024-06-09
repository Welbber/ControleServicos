package br.com.vital.controle_servico.itens.domain;

import lombok.Getter;

@Getter
public enum ItemType {
    PECA("PEÇA"), SERVICO_EM_GERAL("SERVIÇO EM GERAL"), ANALISE("ANÁLISE"), LIMPEZA("LIMPEZA"), OUTROS("OUTROS");
    private final String type;

    ItemType(String type) {
        this.type = type;
    }

}
