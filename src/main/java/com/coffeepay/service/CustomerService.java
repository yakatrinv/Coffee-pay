package com.coffeepay.service;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import com.coffeepay.repository.CustomerRepository;
import com.coffeepay.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Role role = roleRepository.findByName(CUSTOMER).orElse(null);
        Customer customer = modelMapper.map(customerDto, CUSTOMER_CLASS);
        User user = customer.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (role != null) {
            roles.add(role);
        }
        user.setRoles(roles);

        return modelMapper.map(customerRepository.save(customer), CUSTOMER_DTO_CLASS);
    }
}
