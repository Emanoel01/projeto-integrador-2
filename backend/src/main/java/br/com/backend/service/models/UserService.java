package br.com.backend.service.models;

import br.com.backend.repository.models.User;

public interface UserService {

    User findByEmail(String email);

}
