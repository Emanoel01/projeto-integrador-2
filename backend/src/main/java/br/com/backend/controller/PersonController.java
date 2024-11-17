package br.com.backend.controller;

import br.com.backend.domain.models.person.PersonDTO;
import br.com.backend.repository.models.Person;
import br.com.backend.service.models.PersonService;
import br.com.backend.service.models.impl.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController()
@RequestMapping("/person")
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    private ResponseEntity createPerson(@RequestBody PersonDTO personDTO){
        try{
            Person person = personService.createPerson(personDTO);

            return ResponseEntity.created(URI.create("")).body(person);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getPersonInfo/{email}")
    private ResponseEntity getPersonInfo(@PathVariable String email){
        try{
            Person person = personService.getPersonInfo(email);

            return ResponseEntity.created(URI.create("")).body(person);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
