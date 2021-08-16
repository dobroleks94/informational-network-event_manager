package com.dbdiploma.services;

import com.dbdiploma.entities.User;
import com.dbdiploma.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public void update(User user){
        userRepository.save(user);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
