package com.capgemini.wsb.fitnesstracker.user.model.dto;

import jakarta.annotation.Nullable;

public record SmallUserDto(@Nullable Long id, String firstName, String lastName) {

}