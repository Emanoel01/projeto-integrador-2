package br.com.backend.domain.models.address;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {
    private String cep;
    private String street;
    private String state;
    private String neighbourhood;
    private String city;
    private String number;
}
