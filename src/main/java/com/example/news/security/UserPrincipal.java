package com.example.news.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserPrincipal {

    private Long id;
    private String name;
    private String email;
    private String password;

    public UserPrincipal(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = "[PROTECTED]";
    }

}
