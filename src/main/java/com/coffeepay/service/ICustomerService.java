package com.coffeepay.service;

import com.coffeepay.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    List<CustomerDto> getAllCustomers();

    void save(CustomerDto customerDto);

    CustomerDto findByUsername(String username);

    CustomerDto findById(Long id);

    void update(CustomerDto customerDto);
}
