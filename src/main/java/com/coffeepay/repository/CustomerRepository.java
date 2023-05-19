package com.coffeepay.repository;

import com.coffeepay.model.Customer;
import com.coffeepay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);

    @Query(value = "SELECT customer FROM Customer customer WHERE customer.user.username = ?1")
    Optional<Customer> findByUsername(String username);

    @Query(value = "SELECT customer FROM Customer customer " +
            "WHERE customer.user in " +
            "( SELECT user FROM User user join user.roles role " +
            "WHERE user.username = ?1 and role.name = ?2 )")
    Optional<Customer> findByUsernameWithRole(String username, String role);
}