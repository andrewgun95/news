package com.example.news.service.common.impl;

import com.example.news.service.common.dto.PageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of page mapper
 */
public class PageMapperImpl {

    private PageMapperImpl() {
    }

    public static Pageable constructPageable(int page, int limit, Collection<String> sortBy) {
        return PageRequest.of(page, limit, constructSort(sortBy));
    }

    public static <T> PageDTO<T> constructResponse(Collection<T> data, int currentPage, int totalPages, long totalElements) {
        return new PageDTO<>(data, currentPage, totalPages, totalElements);
    }

    public static Sort constructSort(Collection<String> sorts) {
        List<Sort.Order> orders = sorts
                .stream()
                .map(sort -> {
                    String[] pairOfSort = sort.split(":");
                    String property = pairOfSort[0];

                    try {
                        String direction = pairOfSort[1];

                        if (direction != null && direction.equalsIgnoreCase("asc")) {
                            return Sort.Order.asc(property);
                        } else {
                            return Sort.Order.desc(property);
                        }

                    } catch (IndexOutOfBoundsException e) {
                        return Sort.Order.asc(property);
                    }

                })
                .collect(Collectors.toList());

        return Sort.by(orders);
    }

}
