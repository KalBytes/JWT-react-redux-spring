package com.jwt.main.user.services;

import com.jwt.main.exceptions.ResourceNotFoundException;
import com.jwt.main.models.MessageResponse;
import com.jwt.main.user.enums.ERole;
import com.jwt.main.user.models.Role;
import com.jwt.main.user.models.User;
import com.jwt.main.user.repositories.RoleRepository;
import com.jwt.main.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<MessageResponse> signupUser(final User user) {
        final Set<Role> roles = new HashSet<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        user.setUsername(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        final Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public User getUserByEmail(final String userEmail) {
        final User user = userRepository.findByEmail(userEmail).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("User with email: %s not found", userEmail));
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
