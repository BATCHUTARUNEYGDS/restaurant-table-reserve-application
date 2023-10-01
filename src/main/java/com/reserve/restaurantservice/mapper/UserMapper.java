package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.UserDto;
import com.reserve.restaurantservice.entities.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDto> entityToDtos(List<User> user);

    UserDto entityToDto(User user);

    @InheritInverseConfiguration
    User dtoToEntity(UserDto userDto);

}
