package com.example.news.service.user.mapper;

import com.example.news.entity.User;
import com.example.news.service.common.GenericMapper;
import com.example.news.service.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = RoleMapper.class
)
public interface UserMapper extends GenericMapper<UserDTO, User> {

    @Mapping(target = "email", ignore = true)
    void updateEntity(UserDTO dto, @MappingTarget User user);

}
