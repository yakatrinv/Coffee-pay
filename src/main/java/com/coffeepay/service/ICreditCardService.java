package com.coffeepay.service;

import com.coffeepay.dto.CreditCardDto;

import java.util.List;

public interface ICreditCardService {
    List<CreditCardDto> getAllCreditCards();

    void save(CreditCardDto creditCardDto, String username);

    CreditCardDto findById(Long id);

    void deleteById(long id);

    List<CreditCardDto> getAllByUsername(String username);
}
