package br.com.backend.repository.interfaces;

import br.com.backend.repository.models.ProductBusinessAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductBusinessAddressRepository extends JpaRepository<ProductBusinessAddress, Long> {
    Optional<ProductBusinessAddress> findByProductIdAndBusinessAddressBusinessIdAndBusinessAddressAddressId(Long productId, Long businessId, Long addressId);
}
