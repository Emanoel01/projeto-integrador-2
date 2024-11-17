package br.com.backend.controller;

import br.com.backend.domain.enums.ExceptionsMessagesEnum;
import br.com.backend.domain.models.ErrorResponse;
import br.com.backend.domain.models.address.AddressDTO;
import br.com.backend.domain.models.business.BusinessDTO;
import br.com.backend.domain.models.business.GetBusinessInfoResponse;
import br.com.backend.domain.models.business.UpdateBusinessRequestDTO;
import br.com.backend.domain.models.product.ProductDTO;
import br.com.backend.repository.models.Business;
import br.com.backend.repository.models.BusinessAddress;
import br.com.backend.repository.models.ProductBusinessAddress;
import br.com.backend.service.models.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/business")
public class BusinessController {

    private static Logger logger = LoggerFactory.getLogger(BusinessController.class);
    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping
    private ResponseEntity saveBusiness(@RequestBody BusinessDTO businessDTO){
        try{
            Business business = businessService.saveBusiness(businessDTO);
            return ResponseEntity.created(URI.create("")).body(business);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getBusinessInfo/{email}")
    private ResponseEntity getBusinessInfo(@PathVariable String email){
        try{
            List<BusinessAddress> businessAddressList = businessService.getBusinessInfo(email);

            GetBusinessInfoResponse getBusinessInfoResponse = new GetBusinessInfoResponse();

            getBusinessInfoResponse.setBusiness(businessAddressList.get(0).getBusiness());
            getBusinessInfoResponse.setAddresses(businessAddressList.stream().map(BusinessAddress::getAddress).toList());

            return ResponseEntity.ok(getBusinessInfoResponse);

        }catch (Exception e){
            logger.error(e.getMessage());
            if("USER_NOT_FOUND".equals(e.getMessage()))
                return ResponseEntity.badRequest().body(ErrorResponse.builder().message("Empresa nao encontrada com o email enviado").build());

            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{businessId}")
    private ResponseEntity updateBusinessInfo(@PathVariable Long businessId, @RequestBody UpdateBusinessRequestDTO updateBusinessRequestDTO){
        try{
            Business business  = businessService.updateBusiness(businessId, updateBusinessRequestDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            if(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage().equalsIgnoreCase(e.getMessage()))
                return ResponseEntity.badRequest().body(ErrorResponse.builder().message("Não foi possível encontrar a loja mencionada").build());
            return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Por favor entre em contato"));
        }
    }

    @PutMapping("/address/{businessId}")
    private ResponseEntity addAddress(@PathVariable Long businessId, @RequestBody AddressDTO addressDTO){
        try{
            BusinessAddress address =businessService.addNewAddressToBusiness(businessId, addressDTO);
            return ResponseEntity.ok().body(address);
        }catch (Exception e){
            if(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage().equalsIgnoreCase(e.getMessage()))
                return ResponseEntity.badRequest().body(ErrorResponse.builder().message("Não foi possível encontrar a loja mencionada").build());
            return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Por favor entre em contato"));
        }
    }

    @PostMapping("/product?business={businessId}&address={addressId}")
    private ResponseEntity addNewProduct(@RequestParam Long businessId, @RequestParam Long addressId, @RequestBody ProductDTO productDTO){
        try{
            ProductBusinessAddress address =businessService.addNewProduct(businessId, addressId, productDTO);
            return ResponseEntity.ok().body(address);
        }catch (Exception e){
            if(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage().equalsIgnoreCase(e.getMessage()))
                return ResponseEntity.badRequest().body(ErrorResponse.builder().message("Não foi possível encontrar a loja mencionada").build());
            return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Por favor entre em contato"));
        }
    }

    @PostMapping("/productOnOtherAddress?business={businessId}&address={addressId}&product={productId}")
    private ResponseEntity addProductOnExistAddress(@RequestParam Long businessId, @RequestParam Long addressId, @RequestParam Long productId){
        try{
            ProductBusinessAddress address =businessService.addProductOnExistAddress(businessId, addressId, productId);
            return ResponseEntity.ok().body(address);
        }catch (Exception e){
            if(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage().equalsIgnoreCase(e.getMessage()))
                return ResponseEntity.badRequest().body(ErrorResponse.builder().message("Não foi possível encontrar a loja mencionada").build());
            return ResponseEntity.internalServerError().body(ErrorResponse.builder().message("Por favor entre em contato"));
        }
    }
}
