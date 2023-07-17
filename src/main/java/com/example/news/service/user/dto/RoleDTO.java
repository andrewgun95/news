package com.example.news.service.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleDTO {

    @NotEmpty(message = "Role name can't be null or empty")
    private String name;

}
