package com.doc.swagger.SwaggerDocumentation.mapper;

import com.doc.swagger.SwaggerDocumentation.dto.UserDto;
import com.doc.swagger.SwaggerDocumentation.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getUserName(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getAdmin(),
                userDto.getRole()
        );
    }

    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getAdmin(),
                user.getRole()
        );
    }
}
