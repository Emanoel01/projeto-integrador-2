package br.com.backend.service.models;

import br.com.backend.domain.models.address.AddressDTO;
import br.com.backend.domain.models.business.BusinessDTO;
import br.com.backend.domain.models.business.UpdateBusinessRequestDTO;
import br.com.backend.domain.models.product.ProductDTO;
import br.com.backend.repository.models.Business;
import br.com.backend.repository.models.BusinessAddress;
import br.com.backend.repository.models.ProductBusinessAddress;

import java.util.List;

public interface BusinessService {

    Business saveBusiness(BusinessDTO businessDTO);

    List<BusinessAddress> getBusinessInfo(String email) throws Exception;

    Business updateBusiness(Long businessId,UpdateBusinessRequestDTO updateBusinessRequestDTO)throws Exception;

    BusinessAddress addNewAddressToBusiness(Long businessId, AddressDTO addressDTO) throws Exception;

    ProductBusinessAddress addNewProduct(Long businessId, Long addressId, ProductDTO productDTO) throws Exception;

    ProductBusinessAddress addProductOnExistAddress(Long businessId, Long addressId, Long productId) throws Exception;

}
