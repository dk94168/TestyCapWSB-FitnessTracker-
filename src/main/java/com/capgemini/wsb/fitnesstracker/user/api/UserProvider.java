package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallEmailUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUsersDateDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    UserDto getUser(Long userId);

    List<SmallEmailUserDto> getUsersByEmail(String email);

    List<UserDto> findAllUsers();

    List<SmallUserDto> findSmallAllUsers();

    SmallEmailUserDto getUserByEmailIgnore(String email);

    SmallEmailUserDto getUserByEmailSearch(String email);

    List<SmallUsersDateDto> findUsersByBirthdate(LocalDate birthdate);
}
