package br.com.backend.repository.interfaces;

import br.com.backend.repository.models.LegalRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalRepresentativeRepository extends JpaRepository<LegalRepresentative, Long> {
}
