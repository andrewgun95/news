package com.example.news.service.file.mapper;

import com.example.news.entity.File;
import com.example.news.service.common.GenericMapper;
import com.example.news.service.file.dto.FileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface FileMapper extends GenericMapper<FileDTO, File> {

    @Override
    FileDTO mapEntityToDTO(File entity);

}
