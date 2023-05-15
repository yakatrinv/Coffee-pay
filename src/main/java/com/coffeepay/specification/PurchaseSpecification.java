package com.coffeepay.specification;

import com.coffeepay.model.Product_;
import com.coffeepay.model.Purchase;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PurchaseSpecification {
    public static Specification<Purchase> betweenDate(LocalDate dateFrom, LocalDate dateTo) {
        //проверка на даты
//        System.out.println(dateFrom);
//        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
//                .between(root.get(Product_.PRICE), dateFrom, dateTo));
        return null;
    }
}
