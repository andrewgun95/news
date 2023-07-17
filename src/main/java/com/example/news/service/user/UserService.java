package com.example.news.service.user;

import com.example.news.service.common.CrudService;
import com.example.news.service.common.PageService;
import com.example.news.service.common.exception.DataNotFoundException;
import com.example.news.service.user.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService<UserDTO>, PageService<UserDTO> {

    UserDTO addRole(Long userId, List<String> roleNames) throws DataNotFoundException;

}
