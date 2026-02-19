package com.ecommerce.vichika.service;

import com.ecommerce.vichika.dto.ChangePasswordRequest;
import com.ecommerce.vichika.dto.RegisterUserRequest;
import com.ecommerce.vichika.dto.UpdateUserRequest;
import com.ecommerce.vichika.dto.UserDto;
import com.ecommerce.vichika.entities.Role;
import com.ecommerce.vichika.exceptions.BadRequestExceptions;
import com.ecommerce.vichika.exceptions.ResourceNotFoundExceptions;
import com.ecommerce.vichika.mapper.UserMapper;
import com.ecommerce.vichika.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers(String sort) {
        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort).ascending())
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));
        return userMapper.toDto(user);
    }

    public UserDto createUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new BadRequestExceptions("Email already exists");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));
        userMapper.updateUser(request,user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));
        userRepository.delete(user);
    }

    public void changePassword(Long id,ChangePasswordRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));
        if (!user.getPassword().equals(request.oldPassword())){
            throw new ResourceNotFoundExceptions("Old password not match");
        }
        user.setPassword(request.newPassword());
        userRepository.save(user);
    }

}