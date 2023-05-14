package com.coffeepay.repository;

import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT user FROM User user join user.roles role " +
            "WHERE user.username = ?1 and role.name = ?2")
    Optional<User> findByUsernameAndRolesIs(String username, String role);
}
