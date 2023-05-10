package com.coffeepay.repository;

import com.coffeepay.model.CreditCard;
import com.coffeepay.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> getAllByCustomer (Customer customer);
}
