package com.example.news.service.file.impl;

import com.example.news.entity.File;
import com.example.news.repository.FileRepository;
import com.example.news.service.common.dto.PageDTO;
import com.example.news.service.common.impl.PageMapperImpl;
import com.example.news.service.file.FileService;
import com.example.news.service.file.dto.FileDTO;
import com.example.news.service.file.mapper.FileMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @Transactional
    @Override
    public boolean upload(String group, List<MultipartFile> multipartFiles, int maxSize) throws IOException {
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            File file = fileRepository.findByNameAndGroup(multipartFile.getOriginalFilename(), group)
                    .orElseGet(() -> {
                        File newFile = new File();
                        newFile.setName(multipartFile.getOriginalFilename());
                        newFile.setGroup(group);
                        return newFile;
                    });
            if (maxSize > 0 && multipartFile.getBytes().length > maxSize * 1024) { // max size in KB
                return false;
            }
            file.setData(multipartFile.getBytes());

            files.add(file);
        }

        fileRepository.saveAll(files);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<FileDTO> get(Integer page, Integer limit, String search, Collection<String> sortBy) {
        String keyword = "%" + search.toLowerCase() + "%";

        Page<File> filePage = fileRepository.findByNameLikeIgnoreCaseOrGroupLikeIgnoreCase(
                keyword, keyword, PageMapperImpl.constructPageable(page, limit, sortBy)
        );

        List<FileDTO> fileDTOs = fileMapper.mapEntitiesToDTOs(filePage.getContent());
        return PageMapperImpl.constructResponse(fileDTOs, page, filePage.getTotalPages(), filePage.getTotalElements());
    }
}
