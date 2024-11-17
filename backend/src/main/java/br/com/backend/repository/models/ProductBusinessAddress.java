package br.com.backend.repository.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_business_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBusinessAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "business_address_id", referencedColumnName = "id")
    private BusinessAddress businessAddress;

}
