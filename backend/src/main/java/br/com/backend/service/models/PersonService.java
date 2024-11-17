package br.com.backend.service.models;

import br.com.backend.domain.models.person.PersonDTO;
import br.com.backend.repository.models.BusinessAddress;
import br.com.backend.repository.models.Person;

import java.util.List;

public interface PersonService {

    Person createPerson(PersonDTO personDTO) throws Exception;

    Person getPersonInfo(String email) throws Exception;

}
