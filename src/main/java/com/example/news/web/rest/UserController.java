package com.example.news.web.rest;

import com.example.news.service.common.exception.DataNotFoundException;
import com.example.news.service.user.UserService;
import com.example.news.service.user.dto.UserDTO;
import com.example.news.web.rest.response.BaseResponse;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequestMapping("v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdate(@RequestBody @Validated UserDTO dto) throws DataNotFoundException {
        userService.createOrUpdate(dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/{id}/role")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRole(@PathVariable(name = "id") Long userId, @RequestBody @NotEmpty List<String> rolesName) throws DataNotFoundException {
        userService.addRole(userId, rolesName);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<BaseResponse> getUsers(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
                                                 @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                                 @RequestParam(value = "sorts", defaultValue = "", required = false) Collection<String> sorts) {
        return ResponseEntity.ok(BaseResponse.of(userService.get(page, limit, search, sorts)));
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getUserDetails(@PathVariable(name = "id") Long id) throws DataNotFoundException {
        return ResponseEntity.ok(
                BaseResponse.of(userService.findByOne(id))
        );
    }

}
