package com.example.news.service.post.mapper;

import com.example.news.entity.Post;
import com.example.news.service.common.GenericMapper;
import com.example.news.service.post.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = CommentMapper.class
)
public interface PostMapper extends GenericMapper<PostDTO, Post> {

    @Mapping(source = "entity.user.id", target = "userId")
    @Override
    PostDTO mapEntityToDTO(Post entity);

    void updateEntity(PostDTO dto, @MappingTarget Post entity);

}
