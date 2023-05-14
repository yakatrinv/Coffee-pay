package com.coffeepay.specification;

import com.coffeepay.model.Product;
import com.coffeepay.model.Product_;
import org.springframework.data.jpa.domain.Specification;

import static util.DataGeneral.PERCENT_STRING;

public class ProductSpecification {
    public static Specification<Product> likeName(String name) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Product_.NAME),
                        PERCENT_STRING +
                                name +
                                PERCENT_STRING));
    }

    public static Specification<Product> betweenPrice(Float minPrice, Float maxPrice) {
        if (minPrice==0 && maxPrice==0) {
            return null;
        } else if (maxPrice==0) {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .ge(root.get(Product_.PRICE), minPrice));
        } else if (minPrice ==0 ){
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .le(root.get(Product_.PRICE), maxPrice));
        } else {
            return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .between(root.get(Product_.PRICE), minPrice, maxPrice));
        }
    }
}
