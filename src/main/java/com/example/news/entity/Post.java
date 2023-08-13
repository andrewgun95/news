package com.example.news.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "posts")
@NamedEntityGraph(
        name = "post-graph",
        attributeNodes = {
                @NamedAttributeNode("subject"),
                @NamedAttributeNode(value = "comments", subgraph = "comments-graph"),
                @NamedAttributeNode("user"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "comments-graph",
                        attributeNodes = @NamedAttributeNode("user")
                )
        }
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
