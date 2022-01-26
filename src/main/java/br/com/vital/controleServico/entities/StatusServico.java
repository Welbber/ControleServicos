package br.com.vital.controleServico.entities;

public enum StatusServico {
    PENDENTE(1), EM_ANDAMENTO(2), CONCLUIDO(3), RETORNO(4);

    private int status;

    private StatusServico(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
