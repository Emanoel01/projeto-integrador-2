package br.com.backend.domain.enums;

public enum ExceptionsMessagesEnum {

    BUSINESS_NOT_FOUND("BUSINESS_NOT_FOUND"),
    BUSINESS_TYPE_NOT_FOUND("BUSINESS_TYPE_NOT_FOUND"),
    BUSINESS_ADDRESS_NOT_FOUND("BUSINESS_ADDRESS_NOT_FOUND"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND"),
    PRODUCT_BUSINESS_ADDRESS_NOT_FOUND("PRODUCT_BUSINESS_ADDRESS_NOT_FOUND"),
    PERSON_NOT_FOUND("PERSON_NOT_FOUND"),
    ORDER_STATUS_NOT_FOUND("ORDER_STATUS_NOT_FOUND"),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND");

    private final String message;

    ExceptionsMessagesEnum(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
