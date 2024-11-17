package br.com.backend.domain.enums;

public enum BusinessTypeEnum {

    LANCHES_NO_GERAL(1, "lanches"),
    HAMBURGUER_APENAS(2, "hamburguers"),
    PASTEIS(3, "pasteis"),
    CACHORRO_QUENTE(4, "cachorro quente"),
    LANCHES_NATURAIS(5, "lanches naturais");

    private int typeId;
    private String description;

    BusinessTypeEnum(int typeId, String description){
        this.typeId = typeId;
        this.description = description;
    }

    public int getTypeId() {return this.typeId;}
    public String getDescription(){return this.description;}

}
