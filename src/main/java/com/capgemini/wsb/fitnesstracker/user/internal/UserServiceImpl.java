package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.model.dto.CreateUserRequest;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallEmailUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUsersDateDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User userDelete = optionalUser.get();
            userRepository.delete(userDelete);
            log.info("Deleting user with ID {}", userId);
        } else {
            log.info("User with ID: " + userId + " does not exist!");
            throw new IllegalArgumentException("User with ID: " + userId + " does not exist!");
        }
    }

    @Override
    public void updateUser(Long userId, User updateUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));


        if (updateUser.getFirstName() != null) {
            user.setFirstName(updateUser.getFirstName());
        }

        if (updateUser.getLastName() != null) {
            user.setLastName(updateUser.getLastName());
        }

        if (updateUser.getEmail() != null) {
            user.setEmail(updateUser.getEmail());
        }

        if (updateUser.getBirthdate() != null) {
            user.setBirthdate(updateUser.getBirthdate());
        }

        log.info("Update User Data with ID {}", userId);
        userRepository.save(user);
    }

    @Override
    public UserDto addUser(CreateUserRequest request) {
        User user = UserMapper.fromRequest(request);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto getUser(Long userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<SmallEmailUserDto> getUsersByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    @Override
    public SmallEmailUserDto getUserByEmailIgnore(final String email) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(UserMapper::smallEmailToDto)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
    }

    @Override
    public SmallEmailUserDto getUserByEmailSearch(final String email) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .findFirst()
                .map(UserMapper::smallEmailToDto)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<SmallUserDto> findSmallAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::smallToDto)
                .toList();
    }

    @Override
    public List<SmallUsersDateDto> findUsersByBirthdate(LocalDate birthdate) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(birthdate))
                .map(UserMapper::smallUsersDateDto)
                .toList();
    }

}