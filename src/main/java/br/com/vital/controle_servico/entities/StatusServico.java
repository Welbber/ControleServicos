package br.com.vital.controle_servico.entities;

public enum StatusServico {
    PENDENTE("PENDENTE"), EM_ANDAMENTO("EM ANDAMENTO"), CONCLUIDO("CONCLUÍDO"), RETORNO("RETORNO");

    private String status;

    private StatusServico(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
