package com.coffeepay.specification;

import com.coffeepay.model.Role;
import com.coffeepay.model.Role_;
import org.springframework.data.jpa.domain.Specification;

import static util.DataGeneral.PERCENT_STRING;

public class RoleSpecification {
    public static Specification<Role> likeName(String name) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Role_.NAME),
                        PERCENT_STRING +
                                name +
                                PERCENT_STRING));
    }
}
