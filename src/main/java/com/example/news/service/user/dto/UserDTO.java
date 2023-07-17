package com.example.news.service.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Username can't be empty or null")
    private String name;

    @NotEmpty(message = "Password can't be empty or null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotEmpty(message = "Email can't be empty or null")
    @Email(message = "Email is invalid")
    private String email;


    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Birth can't be null")
    private LocalDate birth;

    @NotNull(message = "Active can't be null")
    private Boolean active = Boolean.TRUE;

    private List<RoleDTO> roles = new ArrayList<>();

}
