package com.example.news.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/authentication/login") || request.getServletPath().equals("/api/authentication/token/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                try {
                    String accessToken = authorizationHeader.substring("Bearer ".length());

                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();

                    // Decode JWT Access Token
                    DecodedJWT decodedJWT = verifier.verify(accessToken);

                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    UserPrincipal userPrincipal = new ObjectMapper().convertValue(
                            decodedJWT.getClaim("principal").asMap(),
                            UserPrincipal.class
                    );

                    // Set Authentication Context
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    userPrincipal,
                                    null,
                                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                    );

                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException e) {
                    response.sendError(FORBIDDEN.value(), "Token is expire");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}
