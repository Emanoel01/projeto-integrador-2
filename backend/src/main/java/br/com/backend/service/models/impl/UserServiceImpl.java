package br.com.backend.service.models.impl;

import br.com.backend.repository.interfaces.UserRepository;
import br.com.backend.repository.models.User;
import br.com.backend.service.models.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
