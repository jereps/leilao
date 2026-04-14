package com.youtan.leilao.controller;

import com.youtan.leilao.config.TokenConfig;
import com.youtan.leilao.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/test")
public class TesteController {

        private final UserService userService;
        private final AuthenticationManager authenticationManager;
        private final TokenConfig tokenConfig;



        @GetMapping("/admin")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity getAdmim(){
            return ResponseEntity.ok().body("Usuario admin");
        }


        @GetMapping("/user")
        @PreAuthorize("hasAnyRole('ADMIN','USER')")
        public ResponseEntity getUser(){
            return ResponseEntity.ok().body("Usuario user");
        }

}
