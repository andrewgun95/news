package com.example.news.service.post.impl;

import com.example.news.entity.Comment;
import com.example.news.entity.Post;
import com.example.news.entity.User;
import com.example.news.repository.CommentRepository;
import com.example.news.repository.PostRepository;
import com.example.news.repository.UserRepository;
import com.example.news.security.SecurityHelper;
import com.example.news.service.common.dto.PageDTO;
import com.example.news.service.common.exception.DataNotFoundException;
import com.example.news.service.common.impl.PageMapperImpl;
import com.example.news.service.post.CommentService;
import com.example.news.service.post.dto.CommentDTO;
import com.example.news.service.post.mapper.CommentMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(UserRepository userRepository,
                              PostRepository postRepository,
                              CommentRepository commentRepository, CommentMapper commentMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional
    @Override
    public CommentDTO createOrUpdate(CommentDTO dto) throws DataNotFoundException {
        Comment comment;
        if (!ObjectUtils.isEmpty(dto.getId())) {
            comment = commentRepository.findById(dto.getId()).orElseThrow(() -> new DataNotFoundException("Comment is not found."));
            commentMapper.updateEntity(dto, comment);
        } else {
            comment = commentRepository.save(commentMapper.mapDTOtoEntity(dto));
        }

        Post post = postRepository.findById(dto.getPostId()).orElseThrow(() -> new DataNotFoundException("Post is not found."));
        comment.setPost(post);
        User user = userRepository.findById(SecurityHelper.getPrincipal().getId()).orElse(null);
        comment.setUser(user);

        return commentMapper.mapEntityToDTO(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDTO findByOne(Long id) throws DataNotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Comment is not found"));
        return commentMapper.mapEntityToDTO(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> findByAll() {
        return commentMapper.mapEntitiesToDTOs(commentRepository.findAll());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<CommentDTO> get(Integer page, Integer limit, String search, Collection<String> sortBy) {
        String keyword = "%" + search.toLowerCase() + "%";

        Page<Comment> commentPage = commentRepository.findByReplyLikeIgnoreCase(keyword,
                PageMapperImpl.constructPageable(page, limit, sortBy)
        );

        List<CommentDTO> commentDTOs = commentMapper.mapEntitiesToDTOs(commentPage.getContent());
        return PageMapperImpl.constructResponse(commentDTOs, page, commentPage.getTotalPages(), commentPage.getTotalElements());
    }

}
