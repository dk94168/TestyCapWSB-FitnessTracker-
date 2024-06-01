package com.capgemini.wsb.fitnesstracker.user.model.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String email;
}
