package com.example.news.repository;

import com.example.news.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {

    Optional<File> findByNameAndGroup(String name, String group);

    Page<File> findByNameLikeIgnoreCaseOrGroupLikeIgnoreCase(String name, String group, Pageable pageable);

}
