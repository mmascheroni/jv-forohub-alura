package com.forohub.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forohub.entity.UserAuthor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(UserAuthor userAuthor) {
        Algorithm ALGORITHM = Algorithm.HMAC256(apiSecret);
        try {
            return JWT.create()
                    .withIssuer("foroHub")
                    .withSubject(userAuthor.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(ALGORITHM);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error creating JWT token: " + e.getMessage(), e);
        }
    }

    public String getSubject(String token) {
        if ( token == null ) {
            throw new RuntimeException();
        }

        DecodedJWT verifier;

        try {
            Algorithm ALGORITHM = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(ALGORITHM)
                    .withIssuer("foroHub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid Token");
        }

        if ( verifier.getSubject() == null ) {
            throw new RuntimeException("invalid Verifier");
        }

        return verifier.getSubject();
    }


    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
