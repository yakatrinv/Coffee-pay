package com.coffeepay.service;

import com.coffeepay.dto.CreditCardDto;
import com.coffeepay.dto.CustomerDto;
import com.coffeepay.model.CreditCard;
import com.coffeepay.model.Customer;
import com.coffeepay.repository.CreditCardRepository;
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
    private final ModelMapper modelMapper;

    @Override
    public List<CreditCardDto> findByCustomer(CustomerDto customerDto) {
        Customer customer = customerDto == null ? null :
                modelMapper.map(customerDto, CUSTOMER_CLASS);

        List<CreditCard> creditCardList = creditCardRepository.getAllByCustomer(customer);
        return creditCardList != null ?
                creditCardList
                        .stream()
                        .map(creditCard -> modelMapper.map(creditCard, CREDIT_CARD_DTO_CLASS))
                        .toList() : null;
    }

    @Override
    public void save(CreditCardDto creditCardDto) {
        creditCardRepository.save(modelMapper.map(creditCardDto, CREDIT_CARD_CLASS));
    }

    @Override
    public CreditCardDto fidById(Long id) {
        return modelMapper.map(creditCardRepository.findById(id), CREDIT_CARD_DTO_CLASS);
    }

    @Override
    public void deleteById(long id) {
        creditCardRepository.deleteById(id);
    }
}
