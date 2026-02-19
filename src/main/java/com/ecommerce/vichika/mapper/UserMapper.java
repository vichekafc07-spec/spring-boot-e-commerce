package com.ecommerce.vichika.mapper;

import com.ecommerce.vichika.dto.RegisterUserRequest;
import com.ecommerce.vichika.dto.UpdateUserRequest;
import com.ecommerce.vichika.dto.UserDto;
import com.ecommerce.vichika.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void updateUser(UpdateUserRequest request,@MappingTarget User user);
}
