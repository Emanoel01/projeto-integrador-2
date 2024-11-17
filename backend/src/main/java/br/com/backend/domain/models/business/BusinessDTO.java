package br.com.backend.domain.models.business;


import br.com.backend.domain.models.address.AddressDTO;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class BusinessDTO {

    private String name;
    private String document;
    private String businessName;
    private String businessDocument;
    private String businessDescription;
    private String email;
    private String password;
    private int businessType;
    private AddressDTO address;

}
