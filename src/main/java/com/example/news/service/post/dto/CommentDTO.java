package com.example.news.service.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    @NotNull(message = "Post Id can't be empty or null")
    private Long postId;

    @NotEmpty(message = "Reply can't be empty or null")
    private String reply;

    private Long userId;

}
