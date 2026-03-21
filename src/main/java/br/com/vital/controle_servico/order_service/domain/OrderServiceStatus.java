package br.com.vital.controle_servico.order_service.domain;

public enum OrderServiceStatus {
    PENDING, 
    IN_PROGRESS, 
    WAITING_PARTS,
    READY_FOR_PICKUP,
    COMPLETED, 
    RETURN,
    CANCELED;
}
