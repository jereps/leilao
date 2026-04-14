package com.youtan.leilao.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.youtan.leilao.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Data
@Component
public class TokenConfig {

    //colocar a secret em uma variavel de ambiente do SO
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("userId", user.getId())
                    .withClaim("roles",user.getRoles().stream().map(Enum::name).toList())
                    .withSubject(user.getEmail())
                    .withExpiresAt(getTokenExpiration())
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
        }  catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token ",exception);
        }

    }

    private Instant getTokenExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public Optional<JWTUserData> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithm)
                                    .build()
                                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .roles(decode.getClaim("roles").asList(String.class))
                    .build());

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

    }
}
