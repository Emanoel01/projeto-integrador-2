package br.com.backend.repository.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @OneToOne
    private Person person;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
