package com.example.news.service.common;

import com.example.news.service.common.dto.PageDTO;

import java.util.Collection;

public interface PageService<T> {

    PageDTO<T> get(Integer page, Integer limit, String search, Collection<String> sort);

}
