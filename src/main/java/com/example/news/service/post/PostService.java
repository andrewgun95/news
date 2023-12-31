package com.example.news.service.post;

import com.example.news.service.common.CrudService;
import com.example.news.service.common.PageService;
import com.example.news.service.post.dto.PostDTO;

public interface PostService extends CrudService<PostDTO>, PageService<PostDTO> {
}
