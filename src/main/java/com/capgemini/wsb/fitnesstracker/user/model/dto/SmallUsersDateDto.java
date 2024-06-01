package com.capgemini.wsb.fitnesstracker.user.model.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record SmallUsersDateDto(@Nullable Long id, String firstName, String lastName, LocalDate birthdate) {

}

