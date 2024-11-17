package br.com.backend.domain.models.business;


import br.com.backend.repository.models.Address;
import br.com.backend.repository.models.Business;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBusinessInfoResponse {

    private Business business;
    private List<Address> addresses;

}
