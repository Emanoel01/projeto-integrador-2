package br.com.backend.repository.interfaces;

import br.com.backend.repository.models.BusinessAddress;
import br.com.backend.repository.models.LegalRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessAddressRepository extends JpaRepository<BusinessAddress, Long> {

    List<BusinessAddress> findByBusiness_Id(Long id);

    List<BusinessAddress> findByBusinessLegalRepresentative(LegalRepresentative legalRepresentative);

    Optional<BusinessAddress> findByBusinessIdAndAddressId(Long businessId, Long addressId);
}
