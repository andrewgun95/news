package com.example.news.service.user.impl;

import com.example.news.entity.Role;
import com.example.news.entity.User;
import com.example.news.repository.RoleRepository;
import com.example.news.repository.UserRepository;
import com.example.news.service.common.dto.PageDTO;
import com.example.news.service.common.exception.DataNotFoundException;
import com.example.news.service.common.impl.PageMapperImpl;
import com.example.news.service.user.UserService;
import com.example.news.service.user.dto.UserDTO;
import com.example.news.service.user.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDTO createOrUpdate(UserDTO dto) {
        Optional<User> optional = userRepository.findByEmail(dto.getEmail());
        User user;
        if (optional.isPresent()) {
            user = optional.get();
            userMapper.updateEntity(dto, user);
        } else {
            user = userRepository.save(userMapper.mapDTOtoEntity(dto));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.mapEntityToDTO(user);
    }

    @Transactional
    public UserDTO addRole(Long userId, List<String> roleNames) throws DataNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User is not found"));
        List<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.isEmpty()) {
            throw new DataNotFoundException("Roles is not found");
        }
        roles.forEach(role -> {
            if (!role.getUsers().contains(user)) {
                role.getUsers().add(user);
            }
        });
        return userMapper.mapEntityToDTO(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findByOne(Long id) throws DataNotFoundException {
        return userMapper.mapEntityToDTO(userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User is not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findByAll() {
        return userMapper.mapEntitiesToDTOs(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<UserDTO> get(Integer page, Integer limit, String search, Collection<String> sorts) {
        Page<User> userPage = userRepository.findAll((root, query, builder) -> builder.or( //
                builder.like(root.get("name"), "%" + search.toLowerCase() + "%"), //
                builder.like(root.get("email"), "%" + search.toLowerCase() + "%") //
        ), PageMapperImpl.constructPageable(page, limit, sorts));

        List<UserDTO> userDTOs = userPage.getContent().stream().map(userMapper::mapEntityToDTO).toList();
        return PageMapperImpl.constructResponse(userDTOs, page, userPage.getTotalPages(), userPage.getTotalElements());
    }
}
