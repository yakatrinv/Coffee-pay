package com.coffeepay.repository;

import com.coffeepay.model.TypePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePaymentRepository extends JpaRepository<TypePayment, Integer> {
}
