package br.com.backend.repository.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business_address")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(referencedColumnName = "id", name = "business_id")
    @ManyToOne
    private Business business;

    @JoinColumn(referencedColumnName = "id", name = "address_id")
    @ManyToOne
    private Address address;

    @Column(length = 10)
    private String number;

}
