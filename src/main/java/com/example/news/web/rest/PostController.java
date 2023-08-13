package com.example.news.web.rest;

import com.example.news.service.common.exception.DataNotFoundException;
import com.example.news.service.post.CommentService;
import com.example.news.service.post.PostService;
import com.example.news.service.post.dto.CommentDTO;
import com.example.news.service.post.dto.PostDTO;
import com.example.news.web.rest.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequestMapping("v1/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdate(@RequestBody @Validated PostDTO dto) throws DataNotFoundException {
        postService.createOrUpdate(dto);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<BaseResponse> getPosts(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
                                                 @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                                 @RequestParam(value = "sorts", defaultValue = "", required = false) Collection<String> sorts) {
        return ResponseEntity.ok(BaseResponse.of(postService.get(page, limit, search, sorts)));
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getPostDetails(@PathVariable(name = "id") Long id) throws DataNotFoundException {
        return ResponseEntity.ok(
                BaseResponse.of(postService.findByOne(id))
        );
    }

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdateComment(@RequestBody @Validated CommentDTO dto) throws DataNotFoundException {
        commentService.createOrUpdate(dto);
    }

    @GetMapping("/comment")
    @ResponseBody
    public ResponseEntity<BaseResponse> getComments(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
                                                    @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                                    @RequestParam(value = "sorts", defaultValue = "", required = false) Collection<String> sorts) {
        return ResponseEntity.ok(BaseResponse.of(commentService.get(page, limit, search, sorts)));
    }

    @GetMapping("/comment/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getCommentDetails(@PathVariable(name = "id") Long id) throws DataNotFoundException {
        return ResponseEntity.ok(BaseResponse.of(commentService.findByOne(id)));
    }

}
