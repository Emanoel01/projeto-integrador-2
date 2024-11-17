package br.com.backend.service.models.impl;

import br.com.backend.domain.enums.ExceptionsMessagesEnum;
import br.com.backend.domain.enums.UserTypeEnum;
import br.com.backend.domain.models.address.AddressDTO;
import br.com.backend.domain.models.business.BusinessDTO;
import br.com.backend.domain.models.business.UpdateBusinessRequestDTO;
import br.com.backend.domain.models.product.ProductDTO;
import br.com.backend.repository.interfaces.*;
import br.com.backend.repository.models.*;
import br.com.backend.service.models.BusinessService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final AddressRepository addressRepository;
    private final LegalRepresentativeRepository legalRepresentativeRepository;
    private final LegalRepresentativeUserRepository legalRepresentativeUserRepository;
    private final BusinessAddressRepository businessAddressRepository;
    private final BusinessTypeRepository businessTypeRepository;
    private final ProductRepository productRepository;
    private final ProductBusinessAddressRepository productBusinessAddressRepository;
    private static Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    public BusinessServiceImpl(UserRepository userRepository, BusinessRepository businessRepository, AddressRepository addressRepository, LegalRepresentativeRepository legalRepresentativeRepository, LegalRepresentativeUserRepository legalRepresentativeUserRepository, BusinessAddressRepository businessAddressRepository, BusinessTypeRepository businessTypeRepository, ProductRepository productRepository, ProductBusinessAddressRepository productBusinessAddressRepository) {
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
        this.addressRepository = addressRepository;
        this.legalRepresentativeRepository = legalRepresentativeRepository;
        this.legalRepresentativeUserRepository = legalRepresentativeUserRepository;
        this.businessAddressRepository = businessAddressRepository;
        this.businessTypeRepository = businessTypeRepository;
        this.productRepository = productRepository;
        this.productBusinessAddressRepository = productBusinessAddressRepository;
    }

    @Override
    @Transactional
    public Business saveBusiness(BusinessDTO businessDTO) {

        try{
            LegalRepresentative legalRepresentative = legalRepresentativeRepository.save(
                   LegalRepresentative.builder()
                           .name(businessDTO.getName())
                           .document(businessDTO.getDocument())
                           .build()
            );
            Business business = businessRepository.save(
                    Business.builder()
                            .name(businessDTO.getBusinessName())
                            .businessDescription(businessDTO.getBusinessDescription())
                            .legalRepresentative(legalRepresentative)
                            .document(businessDTO.getBusinessDocument())
                            .build());

            Address address = addressRepository.save(Address
                    .builder()
                        .cep(businessDTO.getAddress().getCep())
                        .street(businessDTO.getAddress().getStreet())
                        .state(businessDTO.getAddress().getState())
                        .neighbourhood(businessDTO.getAddress().getNeighbourhood())
                        .city(businessDTO.getAddress().getCity()).build());

            User user = userRepository.save(User.builder()
                            .email(businessDTO.getEmail())
                            .password(encryptPassword(businessDTO.getPassword()))
                            .role("ADMIN")
                            .userType(UserType
                                    .builder()
                                    .id(UserTypeEnum.BUSINESS.getType())
                                    .description(UserTypeEnum.BUSINESS.name())
                                    .build()).build());

            businessAddressRepository.save(BusinessAddress
                    .builder()
                            .business(business)
                            .address(address)
                            .number(businessDTO.getAddress().getNumber())
                    .build());

            legalRepresentativeUserRepository.save(LegalRepresentativeUser
                    .builder()
                            .legalRepresentative(legalRepresentative)
                            .user(user)
                    .build());

            return business;
        }catch (Exception e){
            logger.error("Erro ao cadastrar empresa ------------------ {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BusinessAddress> getBusinessInfo(String email) throws Exception {
        LegalRepresentativeUser legalRepresentativeUser =legalRepresentativeUserRepository.findByUser_Email(email);

        if(legalRepresentativeUser == null) throw new Exception("USER_NOT_FOUND");

        List<BusinessAddress> businessAddresses = Collections.emptyList();

        if(legalRepresentativeUser.getUser().getUserType().getId().equals(UserTypeEnum.BUSINESS.getType())){
            businessAddresses = businessAddressRepository.findByBusinessLegalRepresentative(legalRepresentativeUser.getLegalRepresentative());
        }


        return businessAddresses;
    }

    @Override
    @Transactional
    public Business updateBusiness(Long businessId, UpdateBusinessRequestDTO updateBusinessRequestDTO) throws Exception {
        try{

            String newBusinessDescription = updateBusinessRequestDTO.getBusinessDescription();
            String newBusinessName = updateBusinessRequestDTO.getBusinessName();
            int newBusinessType =updateBusinessRequestDTO.getBusinessType();

            Optional<Business> businessOptional = businessRepository.findById(businessId);

            if(businessOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage());

            Business business = businessOptional.get();

            Class<? extends UpdateBusinessRequestDTO> classUpdateBusinessRequestDto = updateBusinessRequestDTO.getClass();

            if(newBusinessType != 0) {
                Optional<BusinessType> businessTypeOptional = businessTypeRepository.findById(Long.valueOf(updateBusinessRequestDTO.getBusinessType()));

                if(businessTypeOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage());

                business.setBusinessType(businessTypeOptional.get());
            }

            if(isNullOrEmpty(newBusinessDescription))
                business.setBusinessDescription(updateBusinessRequestDTO.getBusinessDescription());

            if(isNullOrEmpty(newBusinessName))
                business.setName(newBusinessName);


            return businessRepository.saveAndFlush(business);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public BusinessAddress addNewAddressToBusiness(Long businessId, AddressDTO addressDTO) throws Exception{
        try{
            Optional<Business> businessOptional = businessRepository.findById(businessId);

            if(businessOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.BUSINESS_NOT_FOUND.getMessage());

            Address address = addressRepository.save(Address.builder()
                    .cep(addressDTO.getCep())
                    .street(addressDTO.getStreet())
                    .state(addressDTO.getState())
                    .neighbourhood(addressDTO.getNeighbourhood())
                    .city(addressDTO.getCity()).build());

            return businessAddressRepository.save(
                    BusinessAddress.builder()
                            .business(businessOptional.get())
                            .address(address)
                            .number(addressDTO.getNumber())
                            .build());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public ProductBusinessAddress addNewProduct(Long businessId, Long addressId, ProductDTO productDTO) throws Exception {
        try{
            Optional<BusinessAddress> businessAddressOptional = businessAddressRepository.findByBusinessIdAndAddressId(businessId, addressId);

            if(businessAddressOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.BUSINESS_ADDRESS_NOT_FOUND.getMessage());

            Product product = productRepository.save(Product.builder().build());

            return productBusinessAddressRepository.save(
                    ProductBusinessAddress.builder()
                            .businessAddress(businessAddressOptional.get())
                            .product(product)
                            .build());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public ProductBusinessAddress addProductOnExistAddress(Long businessId, Long addressId, Long productId) throws Exception {
        try{

            Optional<BusinessAddress> businessAddressOptional = businessAddressRepository.findByBusinessIdAndAddressId(businessId, addressId);

            if(businessAddressOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.BUSINESS_ADDRESS_NOT_FOUND.getMessage());

            Optional<Product> productOptional = productRepository.findById(productId);

            if(productOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.PRODUCT_NOT_FOUND.getMessage());

            return productBusinessAddressRepository.save(ProductBusinessAddress.builder()
                            .product(productOptional.get())
                            .businessAddress(businessAddressOptional.get())
                    .build());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private String returnGetMethodNameOfField(String fieldName){
        String firstLetterUpperCase = String.valueOf(fieldName.charAt(0)).toUpperCase();
        return "get".concat(firstLetterUpperCase.concat(fieldName.substring(1)));
    }
    private Boolean isNullOrEmpty(String string){
        return string == null || string.isEmpty();
    }
}
