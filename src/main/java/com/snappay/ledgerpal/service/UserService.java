package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.exception.UserRegistrationException;
import com.snappay.ledgerpal.model.UserModel;
import com.snappay.ledgerpal.model.UserRegistrationModel;
import com.snappay.ledgerpal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserModel::build);
    }

    @Transactional
    public UserModel register(UserRegistrationModel model) throws UserRegistrationException {
        if (userRepository.existsByUsername(model.getUsername()))
            throw new UserRegistrationException("selected username is already taken");
        if (userRepository.existsByEmail(model.getEmail()))
            throw new UserRegistrationException("given email is already registered");

        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setUsername(model.getUsername());
        user.setEmail(model.getEmail());
        user.setPassword(passwordEncoder.encode(model.getPassword()));

        user = userRepository.save(user);
        return UserModel.build(user);
    }
}
