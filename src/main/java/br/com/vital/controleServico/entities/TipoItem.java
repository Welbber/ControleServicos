package br.com.vital.controleServico.entities;

public enum TipoItem {
    PECA(1), SERVICO_EM_GERAL(2), ANALISE(3), LIMPEZA(4), OUTROS(5);
    private int tipo;

    private TipoItem(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
}
