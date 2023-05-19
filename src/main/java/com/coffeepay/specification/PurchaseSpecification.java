package com.coffeepay.specification;

import com.coffeepay.model.Customer_;
import com.coffeepay.model.Purchase;
import com.coffeepay.model.Purchase_;
import com.coffeepay.model.User_;
import org.springframework.data.jpa.domain.Specification;

public class PurchaseSpecification {
    public static Specification<Purchase> equalsByCustomer(String username) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Purchase_.CUSTOMER)
                        .get(Customer_.USER)
                        .get(User_.USERNAME), username));
    }
}
