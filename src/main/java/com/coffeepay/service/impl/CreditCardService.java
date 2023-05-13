package com.coffeepay.service.impl;

import com.coffeepay.dto.CreditCardDto;
import com.coffeepay.model.CreditCard;
import com.coffeepay.repository.CreditCardRepository;
import com.coffeepay.repository.CustomerRepository;
import com.coffeepay.service.ICreditCardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static util.DataGeneral.CREDIT_CARD_CLASS;
import static util.DataGeneral.CREDIT_CARD_DTO_CLASS;
import static util.DataGeneral.CUSTOMER_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class CreditCardService implements ICreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(CreditCardDto creditCardDto,String username) {
        CreditCard creditCard = modelMapper.map(creditCardDto, CREDIT_CARD_CLASS);
        creditCard.setCustomer(customerRepository.findByUsername(username).orElse(null));
        creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCardDto fidById(Long id) {
        return modelMapper.map(creditCardRepository.findById(id), CREDIT_CARD_DTO_CLASS);
    }

    @Override
    public List<CreditCardDto> getAllByUsername(String username) {
        return creditCardRepository.getAllByUsername(username)
                .stream()
                .map(creditCards -> modelMapper.map(creditCards,
                        CREDIT_CARD_DTO_CLASS))
                .toList();
    }

    @Override
    public void deleteById(long id) {
        creditCardRepository.deleteById(id);
    }
}
