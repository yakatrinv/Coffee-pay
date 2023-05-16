package com.coffeepay.repository;

import com.coffeepay.model.Customer;
import com.coffeepay.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface DiscountRepository extends JpaRepository<Discount, Integer>, JpaSpecificationExecutor<Discount> {
    @Query(value = "SELECT discount FROM Discount discount " +
            "where discount.summ <= " +
            "(select SUM(summ) FROM Purchase where customer.id=?1)" +
            "ORDER BY discount.percent asc nulls first")
    Optional<Discount> findBySumAndByCustomer(Long id);
}
