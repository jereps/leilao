package com.youtan.leilao.service;

import com.youtan.leilao.DTO.RegisterUserRequest;
import com.youtan.leilao.DTO.RegisterUserResponse;
import com.youtan.leilao.model.User;
import com.youtan.leilao.model.UserRole;
import com.youtan.leilao.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Data
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity register(RegisterUserRequest userRequest) {
        if (userRepository.findUserByEmail(userRequest.email()).isPresent()) return ResponseEntity.badRequest().body("error.user.email.exists");

        User user = new User();
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setNome(userRequest.nome());
        user.setEmail(userRequest.email());

        if (userRequest.role() != null){
            user.setRoles(Set.of(userRequest.role()));
        } else {
            user.setRoles(Set.of(UserRole.ROLE_USER));
        }

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(user.getNome(),user.getEmail()));
    }
}
