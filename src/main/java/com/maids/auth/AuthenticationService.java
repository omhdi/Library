package com.maids.auth;

import com.maids.config.JwtService;
import com.maids.user.Role;
import com.maids.user.User;
import com.maids.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthinticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))// last edit
                .build();
        repo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthinticationResponse.builder()
                .token(jwtToken)
                .expires_in(jwtService.extractExpiration(jwtToken))
                .IssueAt(jwtService.extractIssuedAt(jwtToken))
                .build();
    }

    public AuthinticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (request.getEmail(), request.getPassword()));
        var user = repo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthinticationResponse.builder()
                .token(jwtToken)
                .expires_in(jwtService.extractExpiration(jwtToken))
                .IssueAt(jwtService.extractIssuedAt(jwtToken))
                .build();
    }
}
