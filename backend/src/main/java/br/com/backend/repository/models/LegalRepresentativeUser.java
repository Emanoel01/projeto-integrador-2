package br.com.backend.repository.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="legal_representative_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegalRepresentativeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "legal_representative_id")
    private LegalRepresentative legalRepresentative;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private User user;

}
