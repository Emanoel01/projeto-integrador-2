package br.com.backend.service.models.impl;


import br.com.backend.domain.enums.UserTypeEnum;
import br.com.backend.domain.models.person.PersonDTO;
import br.com.backend.repository.interfaces.PersonRepository;
import br.com.backend.repository.interfaces.PersonUserRepository;
import br.com.backend.repository.interfaces.UserRepository;
import br.com.backend.repository.models.Person;
import br.com.backend.repository.models.PersonUser;
import br.com.backend.repository.models.User;
import br.com.backend.repository.models.UserType;
import br.com.backend.service.models.PersonService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PersonUserRepository personUserRepository;


    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    public PersonServiceImpl(UserRepository userRepository, PersonRepository personRepository, PersonUserRepository personUserRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.personUserRepository = personUserRepository;
    }

    @Override
    @Transactional
    public Person createPerson(PersonDTO personDTO) throws Exception {
        try{
            User user = userRepository.save(User.builder()
                    .email(personDTO.getEmail())
                    .password(encryptPassword(personDTO.getPassword()))
                    .role("USER_DEFAULT")
                    .userType(UserType
                            .builder()
                            .id(UserTypeEnum.PERSON.getType())
                            .description(UserTypeEnum.PERSON.name())
                            .build()).build());

            Person person = personRepository.save(Person.builder()
                            .age(personDTO.getAge())
                            .name(personDTO.getName())
                    .build());

            personUserRepository.save(PersonUser.builder()
                            .person(person)
                            .user(user)
                    .build());

            return person;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Person getPersonInfo(String email) throws Exception {
        try{
            return personUserRepository.findByUserEmail(email).get().getPerson();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
