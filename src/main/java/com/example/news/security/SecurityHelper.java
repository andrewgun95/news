package com.example.news.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

    public static UserPrincipal getPrincipal() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
