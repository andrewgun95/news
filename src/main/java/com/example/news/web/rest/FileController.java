package com.example.news.web.rest;

import com.example.news.service.file.FileService;
import com.example.news.web.rest.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequestMapping("v1/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<BaseResponse> getFiles(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
                                                 @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                                 @RequestParam(value = "sorts", defaultValue = "", required = false) Collection<String> sorts) {
        return ResponseEntity.ok(BaseResponse.of(fileService.get(page, limit, search, sorts)));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(HttpServletRequest httpRequest, @RequestParam(value = "maxSize", defaultValue = "0") int maxSize) throws IOException {
        if (httpRequest instanceof StandardMultipartHttpServletRequest) {
            StandardMultipartHttpServletRequest fileRequest = (StandardMultipartHttpServletRequest) httpRequest;

            Map<String, String[]> parameterMap = fileRequest.getParameterMap();
            if (parameterMap.get("group") != null && parameterMap.get("group").length < 1) {
                return ResponseEntity.badRequest().body("Invalid File Group. File group is not found.");
            }
            String groups = parameterMap.get("group")[0];

            MultiValueMap<String, MultipartFile> fileMap = fileRequest.getMultiFileMap();
            if (fileMap.get("files") != null) {
                List<MultipartFile> files = fileMap.get("files");

                boolean success = fileService.upload(groups, files, maxSize);
                if (success) {
                    return ResponseEntity.noContent().build();
                }

                return ResponseEntity.internalServerError().body("Failed to upload.");
            } else {
                return ResponseEntity.badRequest().body("Invalid Files. Files is not found.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid Content-Type. Content-Type must multipart/form-data.");
        }
    }

}
