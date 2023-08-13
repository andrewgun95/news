package com.example.news.service.post;

import com.example.news.service.common.CrudService;
import com.example.news.service.common.PageService;
import com.example.news.service.post.dto.CommentDTO;

public interface CommentService extends CrudService<CommentDTO>, PageService<CommentDTO> {
}
