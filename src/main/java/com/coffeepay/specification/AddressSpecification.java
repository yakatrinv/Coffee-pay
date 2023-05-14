package com.coffeepay.specification;

import com.coffeepay.model.Address;
import com.coffeepay.model.Address_;
import org.springframework.data.jpa.domain.Specification;

import static util.DataGeneral.PERCENT_STRING;

public class AddressSpecification {
    public static Specification<Address> likeCity(String city) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Address_.CITY),
                        PERCENT_STRING +
                                city +
                                PERCENT_STRING));
    }

    public static Specification<Address> likeStreet(String street) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Address_.STREET),
                        PERCENT_STRING +
                                street +
                                PERCENT_STRING));
    }
}
