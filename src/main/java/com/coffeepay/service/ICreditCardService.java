package com.coffeepay.service;

import com.coffeepay.dto.CreditCardDto;

import java.util.List;

public interface ICreditCardService {
    void save(CreditCardDto creditCardDto, String username);

    CreditCardDto fidById(Long id);

    void deleteById(long id);

    List<CreditCardDto> getAllByUsername(String username);
}
