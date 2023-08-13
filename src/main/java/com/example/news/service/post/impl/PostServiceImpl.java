package com.example.news.service.post.impl;

import com.example.news.entity.Post;
import com.example.news.entity.User;
import com.example.news.repository.PostRepository;
import com.example.news.repository.UserRepository;
import com.example.news.security.SecurityHelper;
import com.example.news.service.common.dto.PageDTO;
import com.example.news.service.common.exception.DataNotFoundException;
import com.example.news.service.common.impl.PageMapperImpl;
import com.example.news.service.post.PostService;
import com.example.news.service.post.dto.PostDTO;
import com.example.news.service.post.mapper.PostMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(UserRepository userRepository,
                           PostRepository postRepository,
                           PostMapper postMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Transactional
    @Override
    public PostDTO createOrUpdate(PostDTO dto) throws DataNotFoundException {
        Post post;
        if (!ObjectUtils.isEmpty(dto.getId())) {
            post = postRepository.findById(dto.getId()).orElseThrow(() -> new DataNotFoundException("Post is not found."));
            postMapper.updateEntity(dto, post);
        } else {
            post = postRepository.save(postMapper.mapDTOtoEntity(dto));
        }

        User user = userRepository.findById(SecurityHelper.getPrincipal().getId()).orElse(null);
        post.setUser(user);

        return postMapper.mapEntityToDTO(post);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDTO findByOne(Long id) throws DataNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Post is not found."));
        return postMapper.mapEntityToDTO(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> findByAll() {
        return postMapper.mapEntitiesToDTOs(postRepository.findAll());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<PostDTO> get(Integer page, Integer limit, String search, Collection<String> sortBy) {
        String keyword = "%" + search.toLowerCase() + "%";
        Page<Post> postPage = postRepository.findBySubjectLikeIgnoreCase(keyword, PageMapperImpl.constructPageable(page, limit, sortBy));

        List<PostDTO> postDTOs = postMapper.mapEntitiesToDTOs(postPage.getContent());
        return PageMapperImpl.constructResponse(postDTOs, page, postPage.getTotalPages(), postPage.getTotalElements());
    }

}
