package com.example.news.service.file.dto;

import lombok.Data;

@Data
public class FileDTO {

    private Long id;

    private String name;

    private String group;

    private byte[] data;

}
