package com.coffeepay.service.impl;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Role;
import com.coffeepay.model.User;
import com.coffeepay.repository.CustomerRepository;
import com.coffeepay.repository.RoleRepository;
import com.coffeepay.repository.UserRepository;
import com.coffeepay.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(address -> modelMapper.map(address, CUSTOMER_DTO_CLASS))
                .toList();
    }

    @Override
    public void save(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, CUSTOMER_CLASS);
        Role role = roleRepository.findByName(CUSTOMER).orElse(null);
        Optional<User> user = Optional.ofNullable(customerDto.getUser())
                .map(userDto -> userRepository
                        .findByUsername(userDto.getUsername()))
                .orElse(null);


        if (user != null && user.isPresent()) {
            Set<Role> roles = user.get().getRoles();
            roles.add(role);
            customer.setUser(user.get());
            customer.getUser().setRoles(roles);
        } else {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            customer.getUser().setRoles(roles);
            customer.getUser().setPassword(bCryptPasswordEncoder
                    .encode(customer.getUser().getPassword()));
        }

        customerRepository.save(customer);
    }

    @Override
    public CustomerDto findByUsername(String username) {
        return customerRepository.findByUsernameWithRole(username, CUSTOMER)
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public CustomerDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(customerRepository::findById)
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void update(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getId()).orElse(null);

        if (customer != null) {
            customer.setName(customerDto.getName());
            customer.setSurname(customerDto.getSurname());
            customer.setPhone(customerDto.getPhone());
            customer.setEmail(customerDto.getEmail());

            customerRepository.save(customer);
        }
    }
}
