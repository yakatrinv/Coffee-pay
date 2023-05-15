package com.coffeepay.specification;

import com.coffeepay.model.Discount;
import com.coffeepay.model.Discount_;
import org.springframework.data.jpa.domain.Specification;

public class DiscountSpecification {
    public static Specification<Discount> betweenSum(int minSum, int maxSum) {
        if (minSum == 0 && maxSum == 0) {
            return null;
        } else if (maxSum == 0) {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .ge(root.get(Discount_.SUM), minSum));
        } else if (minSum == 0) {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .le(root.get(Discount_.SUM), maxSum));
        } else {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .between(root.get(Discount_.SUM), minSum, maxSum));
        }
    }

    public static Specification<Discount> betweenPercent(int minPercent, int maxPercent) {
        if (minPercent == 0 && maxPercent == 0) {
            return null;
        } else if (maxPercent == 0) {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .ge(root.get(Discount_.PERCENT), minPercent));
        } else if (minPercent == 0) {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .le(root.get(Discount_.PERCENT), maxPercent));
        } else {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .between(root.get(Discount_.PERCENT), minPercent, maxPercent));
        }
    }
}
