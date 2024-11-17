package br.com.backend.domain.enums;

public enum UserTypeEnum {

    BUSINESS(1),
    PERSON(2);

    private Integer type;

    UserTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType(){
        return this.type;
    }
}
