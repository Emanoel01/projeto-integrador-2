package br.com.backend.repository.interfaces;

import br.com.backend.repository.models.LegalRepresentativeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalRepresentativeUserRepository extends JpaRepository<LegalRepresentativeUser, Long> {

    LegalRepresentativeUser findByUser_Email(String email);
}
