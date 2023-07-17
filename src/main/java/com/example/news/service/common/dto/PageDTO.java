package com.example.news.service.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for pageable data
 *
 * @param <T> payload data
 */
@Data
public class PageDTO<T> implements Serializable {

    public PageDTO(Collection<T> data, Integer currentPage, Integer totalPages, Long totalElements) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    private Collection<T> data;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;

}
