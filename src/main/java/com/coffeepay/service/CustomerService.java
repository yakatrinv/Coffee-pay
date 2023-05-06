package com.coffeepay.service;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import com.coffeepay.repository.CustomerRepository;
import com.coffeepay.repository.RoleRepository;
import com.coffeepay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static util.DataGeneral.CUSTOMER;
import static util.DataGeneral.CUSTOMER_CLASS;
import static util.DataGeneral.CUSTOMER_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Role role = roleRepository.findByName(CUSTOMER).orElse(null);
        if (role == null) {
            return null;
        }

        if (customerDto.getUser() != null) {
            User findUser = userRepository.findByUsername(
                            customerDto.getUser().getUsername())
                    .orElse(null);

            Customer customer = modelMapper.map(customerDto, CUSTOMER_CLASS);
            User user = customer.getUser();
            Set<Role> roles = new HashSet<>();

            if (findUser == null) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                roles.add(role);
                user.setRoles(roles);

                customer = customerRepository.save(customer);
            } else if (findUser.getRoles() == null) {
                customer.setUser(findUser);
                roles.add(role);
                user.setRoles(roles);

                customer = customerRepository.save(customer);
            } else if (!findUser.getRoles().contains(role)) {
                customer.setUser(findUser);
                roles = findUser.getRoles();
                roles.add(role);
                user.setRoles(roles);

                customer = customerRepository.save(customer);
            }
            return modelMapper.map(customer, CUSTOMER_DTO_CLASS);
        }

        return null;
    }

    @Override
    public CustomerDto findByUsername(String username) {
        Role roleCustomer = roleRepository
                .findByName(CUSTOMER).orElse(null);

        User user = userRepository
                .findByUsernameAndRolesIs(username, roleCustomer)
                .orElse(null);

        Optional<Customer> customer = customerRepository.findByUser(user);

        return customer.isPresent() ?
                modelMapper.map(customer, CUSTOMER_DTO_CLASS) : null;
    }

    @Override
    public CustomerDto findById(Long id) {
        return modelMapper.map(customerRepository.findById(id), CUSTOMER_DTO_CLASS);
    }

    @Override
    public void update(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, CUSTOMER_CLASS);
        customerRepository.save(customer);
    }
}
