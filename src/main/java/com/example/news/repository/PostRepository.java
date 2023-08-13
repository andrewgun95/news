package com.example.news.repository;

import com.example.news.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    @EntityGraph("post-graph")
    Optional<Post> findById(Long id);

    Page<Post> findBySubjectLikeIgnoreCase(String subject, Pageable pageable);

}
