package com.coffeepay.service;

import com.coffeepay.dto.CustomerDto;

public interface ICustomerService {
    CustomerDto save(CustomerDto customerDto);
}
