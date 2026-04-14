package com.youtan.leilao.service;

import com.youtan.leilao.DTO.RegisterUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity register(RegisterUserRequest userRequest);
}
