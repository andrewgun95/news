package com.example.news.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_group")
    private String group;

    @Lob
    @Column(name = "file_data", columnDefinition = "BLOB")
    private byte[] data;

}
