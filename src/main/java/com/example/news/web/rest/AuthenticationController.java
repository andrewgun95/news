package com.example.news.web.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.news.entity.User;
import com.example.news.security.UserPrincipal;
import com.example.news.service.user.dto.LoginDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

            // Generate JWT Access Token
            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutes
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withClaim("principal",
                            new ObjectMapper()
                                    .convertValue(
                                            new UserPrincipal(user.getId(), user.getName(), user.getEmail()),
                                            new TypeReference<Map<String, Object>>() {
                                            }
                                    )
                    )
                    .sign(algorithm);

            Map<String, Object> orderMap = new LinkedHashMap<>();
            orderMap.put("id", user.getId());
            orderMap.put("name", user.getName());
            orderMap.put("email", user.getEmail());
            orderMap.put("accessToken", accessToken);

            return ResponseEntity.ok(orderMap);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage(), e.getCause());
            return ResponseEntity.badRequest().body("Invalid Username or Password");
        }
    }
}
