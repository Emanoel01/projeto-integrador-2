package br.com.backend.domain.enums;

public enum OrderStatusEnum {

    CREATED(1),
    ACCEPTED(2),
    STARTED(3),
    FINALIZED(4),
    NOT_ACCEPTED(5),
    CANCELLED(6);

    private int status;

    OrderStatusEnum (int status){
        this.status = status;
    }

    public long getStatus(){
        return this.status;
    }

}
