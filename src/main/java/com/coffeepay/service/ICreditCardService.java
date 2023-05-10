package com.coffeepay.service;

import com.coffeepay.dto.CreditCardDto;
import com.coffeepay.dto.CustomerDto;
import com.coffeepay.model.CreditCard;

import java.util.List;

public interface ICreditCardService {
    List<CreditCardDto> findByCustomer(CustomerDto customerDto);

    void save(CreditCardDto creditCardDto);

    CreditCardDto fidById(Long id);

    void deleteById(long id);
}
