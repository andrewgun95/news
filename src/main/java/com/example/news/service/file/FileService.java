package com.example.news.service.file;

import com.example.news.service.common.PageService;
import com.example.news.service.file.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService extends PageService<FileDTO> {

    boolean upload(String group, List<MultipartFile> files, int maxSize) throws IOException;

}
