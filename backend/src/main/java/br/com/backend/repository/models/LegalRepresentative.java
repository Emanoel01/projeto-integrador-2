package br.com.backend.repository.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "legal_representative")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegalRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String document;
}
