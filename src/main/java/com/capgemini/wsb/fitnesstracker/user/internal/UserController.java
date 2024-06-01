package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.model.dto.CreateUserRequest;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallEmailUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUserDto;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallUsersDateDto;
import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody User updateUser) {
        try {
            userService.updateUser(userId, updateUser);
            return ResponseEntity.noContent().build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody CreateUserRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(request));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/simple")
    public List<SmallUserDto> getAllSimpleUsers() {
        return userService.findSmallAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/email")
    public List<SmallEmailUserDto> getUsersByEmail(@RequestParam("email") String email) {
        return userService.getUsersByEmail(email);
    }

    @GetMapping("/byEmailIgnore/{email}")
    public SmallEmailUserDto getUserByEmailIgnore(@PathVariable String email) {
        return userService.getUserByEmailIgnore(email);
    }

    @GetMapping("/byEmailSearch/{email}")
    public SmallEmailUserDto getUserByEmailSearch(@PathVariable String email) {
        return userService.getUserByEmailSearch(email);
    }

    @GetMapping("/older/{birthdate}")
    public List<SmallUsersDateDto> findUsersByDate(@PathVariable LocalDate birthdate) {
        return userService.findUsersByBirthdate(birthdate);
    }
}