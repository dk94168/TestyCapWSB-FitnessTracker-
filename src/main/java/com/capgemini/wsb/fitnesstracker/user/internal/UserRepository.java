package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.model.dto.SmallEmailUserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Long> {
    List<SmallEmailUserDto> findAllByEmail(String email);
}
