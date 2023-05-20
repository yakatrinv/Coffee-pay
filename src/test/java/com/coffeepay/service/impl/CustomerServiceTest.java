package com.coffeepay.service.impl;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import com.coffeepay.repository.CustomerRepository;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static util.DataGeneral.CUSTOMER_DTO_CLASS;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CustomerServiceTest {
    public static final String CUSTOMER = "Customer";
    public static final String ERROR_CUSTOMER = "error customer";
    public static final String ROLE_CUSTOMER = "Customer";

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findByUsername() {
        User newUser = User.builder()
                .username(CUSTOMER)
                .build();

        Customer newCustomer = Customer.builder()
                .user(newUser)
                .build();

        customerRepository.save(newCustomer);

        Optional<Customer> optionalCustomer = customerRepository.findByUsername(newUser.getUsername());
        CustomerDto customerDto = optionalCustomer
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null);


        Assertions.assertAll(
                () -> assertNotNull("User not save", newUser.getId()),
                () -> assertNotNull("Customer not save", newCustomer.getId()),
                () -> assertNotNull("Customer not found", customerDto),
                () -> Assert.assertEquals(
                        "Username is not equals",
                        (customerDto != null && customerDto.getUser() != null)
                                ? customerDto.getUser().getUsername() : null,
                        newUser.getUsername())
        );

        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByErrorUsername() {
        User newUser = User.builder()
                .username(CUSTOMER)
                .build();

        Customer newCustomer = Customer.builder()
                .user(newUser)
                .build();

        customerRepository.save(newCustomer);

        CustomerDto customerDto = customerRepository.findByUsername(ERROR_CUSTOMER)
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null);


        Assertions.assertAll(
                () -> assertNotNull("User not save", newUser.getId()),
                () -> assertNotNull("Customer not save", newCustomer.getId()),
                () -> assertNull("Customer found", customerDto)
        );

        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByUsernameWithRole() {
        Role newRole = Role.builder().name(ROLE_CUSTOMER).build();
        roleRepository.save(newRole);
        Set<Role> roles=new HashSet<>();
        roles.add(newRole);

        User newUser = User.builder()
                .username(CUSTOMER)
                .roles(roles)
                .build();

        Customer newCustomer = Customer.builder()
                .user(newUser)
                .build();

        customerRepository.save(newCustomer);

        Optional<Customer> optionalCustomer = customerRepository
                .findByUsernameWithRole(newUser.getUsername(),ROLE_CUSTOMER);
        CustomerDto customerDto = optionalCustomer
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null);


        Assertions.assertAll(
                () -> assertNotNull("User not save", newUser.getId()),
                () -> assertNotNull("Customer not save", newCustomer.getId()),
                () -> assertNotNull("Customer not found", customerDto),
                () -> Assert.assertEquals(
                        "Username is not equals",
                        (customerDto != null && customerDto.getUser() != null)
                                ? customerDto.getUser().getUsername() : null,
                        newUser.getUsername())
        );

        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByErrorUsernameWithRole() {
        Role newRole = Role.builder().name(ROLE_CUSTOMER).build();
        roleRepository.save(newRole);
        Set<Role> roles=new HashSet<>();
        roles.add(newRole);

        User newUser = User.builder()
                .username(CUSTOMER)
                .roles(roles)
                .build();

        Customer newCustomer = Customer.builder()
                .user(newUser)
                .build();

        customerRepository.save(newCustomer);

        Optional<Customer> optionalCustomer = customerRepository
                .findByUsernameWithRole(ERROR_CUSTOMER,ROLE_CUSTOMER);
        CustomerDto customerDto = optionalCustomer
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null);


        Assertions.assertAll(
                () -> assertNotNull("User not save", newUser.getId()),
                () -> assertNotNull("Customer not save", newCustomer.getId()),
                () -> assertNull("Customer found", customerDto)
        );

        customerRepository.deleteAll();
        userRepository.deleteAll();
    }
}