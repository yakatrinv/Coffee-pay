package com.coffeepay.repository;

import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndRolesIs(String username, Role role);
}
