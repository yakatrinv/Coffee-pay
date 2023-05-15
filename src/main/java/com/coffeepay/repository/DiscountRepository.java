package com.coffeepay.repository;

import com.coffeepay.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface DiscountRepository extends JpaRepository<Discount, Integer>, JpaSpecificationExecutor<Discount> {
}
