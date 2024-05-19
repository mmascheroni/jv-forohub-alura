package com.forohub.controller;

import com.forohub.entity.UserAuthenticate;
import com.forohub.entity.UserAuthor;
import com.forohub.security.jwt.JWTDto;
import com.forohub.security.jwt.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticate userAuthenticate) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userAuthenticate.email(), userAuthenticate.password());

        var authenticateUser =  authenticationManager.authenticate(authenticationToken);

        String jwtToken = jwtService.generateToken((UserAuthor) authenticateUser.getPrincipal());

        return ResponseEntity.ok(new JWTDto(jwtToken));
    }
}
