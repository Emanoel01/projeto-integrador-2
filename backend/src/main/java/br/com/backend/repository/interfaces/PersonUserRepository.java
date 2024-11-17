package br.com.backend.repository.interfaces;

import br.com.backend.repository.models.PersonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonUserRepository extends JpaRepository<PersonUser, Long> {

    Optional<PersonUser> findByUserEmail(String email);

}
