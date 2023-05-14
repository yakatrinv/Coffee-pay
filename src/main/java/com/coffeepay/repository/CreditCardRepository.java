package com.coffeepay.repository;

import com.coffeepay.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Query(value = "SELECT creditCard FROM CreditCard creditCard where creditCard.customer.user.username=?1")
    List<CreditCard> getAllByUsername(String username);
}
