package com.coffeepay.service;

import com.coffeepay.dto.CustomerDto;

public interface ICustomerService {
    void save(CustomerDto customerDto);

    CustomerDto findByUsername(String username);

    CustomerDto findById(Long id);

    void update(CustomerDto customerDto);
}
