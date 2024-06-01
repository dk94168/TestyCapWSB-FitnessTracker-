package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.model.dto.CreateUserRequest;
import com.capgemini.wsb.fitnesstracker.user.model.dto.UserDto;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    void deleteUser(Long userId);

    void updateUser(Long userId, User updateUser);

    UserDto addUser(CreateUserRequest request);
}
