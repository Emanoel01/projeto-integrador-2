package br.com.backend.domain.models.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBusinessRequestDTO {
    private String businessName;
    private String businessDocument;
    private String businessDescription;
    private int businessType;
}
