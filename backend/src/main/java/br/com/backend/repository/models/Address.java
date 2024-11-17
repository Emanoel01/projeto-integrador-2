package br.com.backend.repository.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 8)
    private String cep;

    @Column
    private String street;

    @Column
    private String state;

    @Column
    private String neighbourhood;

    @Column
    private String city;

}
