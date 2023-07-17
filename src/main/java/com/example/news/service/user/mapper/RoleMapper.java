package com.example.news.service.user.mapper;

import com.example.news.entity.Role;
import com.example.news.service.common.GenericMapper;
import com.example.news.service.user.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface RoleMapper extends GenericMapper<RoleDTO, Role> {
}
