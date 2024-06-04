package br.com.vital.controle_servico.entities;

public enum TipoItem {
    PECA("PEÇA"), SERVICO_EM_GERAL("SERVIÇO EM GERAL"), ANALISE("ANÁLISE"), LIMPEZA("LIMPEZA"), OUTROS("OUTROS");
    private String tipo;

    private TipoItem(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
