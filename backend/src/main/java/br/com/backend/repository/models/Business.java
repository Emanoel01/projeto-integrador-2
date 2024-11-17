package br.com.backend.repository.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "business")
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 14)
    private String document;

    @Column
    private String name;

    @Column(name = "business_description")
    private String businessDescription;

    @Column(name = "created_at", columnDefinition = "datetime default NOW()")
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "legal_representative_id", referencedColumnName = "id")
    private LegalRepresentative legalRepresentative;

    @ManyToOne
    @JoinColumn(name = "business_type_id", referencedColumnName = "id")
    private BusinessType businessType;

}
