package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.model.dto.CreateUserRequest;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallEmailUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUsersDateDto;
import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    public static User fromRequest(CreateUserRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthdate(request.getBirthdate())
                .email(request.getEmail())
                .build();
    }

    public static SmallUserDto smallToDto(User user) {
        return new SmallUserDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    public static SmallEmailUserDto smallEmailToDto(User user) {
        return new SmallEmailUserDto(user.getId(),
                user.getEmail());
    }

    public static SmallUsersDateDto smallUsersDateDto(User user) {
        return new SmallUsersDateDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate());
    }

    public static User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

}
