package com.example.news.service.post.mapper;


import com.example.news.entity.Comment;
import com.example.news.service.common.GenericMapper;
import com.example.news.service.post.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface CommentMapper extends GenericMapper<CommentDTO, Comment> {

    @Mapping(source = "entity.user.id", target = "userId")
    @Mapping(source = "entity.post.id", target = "postId")
    @Override
    CommentDTO mapEntityToDTO(Comment entity);

    void updateEntity(CommentDTO dto, @MappingTarget Comment entity);

}
