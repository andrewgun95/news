package com.example.news.service.post.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {

    private Long id;

    @NotEmpty(message = "Subject can't be empty or null")
    private String subject;

    private Long userId;

    private List<CommentDTO> comments = new ArrayList<>();

}
