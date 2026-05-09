package dev.raseen.studentmanagement.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.raseen.studentmanagement.dto.AuthRequest;
import dev.raseen.studentmanagement.entity.User;
import dev.raseen.studentmanagement.repository.UserRepository;
import dev.raseen.studentmanagement.security.JwtUtil;
import jakarta.validation.Valid;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, String> register(@Valid User user) {
    	
    	if (repo.findByUsername(user.getUsername()).isPresent()) {
    	    throw new RuntimeException("Username already exists");
    	}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        repo.save(user);
        return Map.of("message", "User registered successfully");
    }
    
    public Map<String, String> login(AuthRequest request) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(request.getUsername());

        return Map.of("token", token);
    }
}