package com.coffeepay.service.impl;

import com.coffeepay.dto.UserDto;
import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import com.coffeepay.repository.RoleRepository;
import com.coffeepay.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static util.DataGeneral.USER_DTO_CLASS;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTest {
    public static final String CUSTOMER = "Customer";
    public static final String ROLE_CUSTOMER = "Customer";

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;


    @Test
    void findByUsernameAndRolesIs() {
        Role newRole = Role.builder().name(ROLE_CUSTOMER).build();
        roleRepository.save(newRole);
        Set<Role> roles = new HashSet<>();
        roles.add(newRole);

        User newUser = User.builder()
                .username(CUSTOMER)
                .roles(roles)
                .build();

        userRepository.save(newUser);

        UserDto userDto = userRepository.findByUsernameAndRolesIs(CUSTOMER, ROLE_CUSTOMER)
                .map(user -> modelMapper.map(user, USER_DTO_CLASS))
                .orElse(null);

        Assertions.assertAll(
                () -> assertNotNull("User not save", newUser.getId()),
                () -> Assert.assertEquals(
                        "Username is not equals",
                        (userDto != null)
                                ? userDto.getUsername() : null,
                        CUSTOMER)
        );

        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
}