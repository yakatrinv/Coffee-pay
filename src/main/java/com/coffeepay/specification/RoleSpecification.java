package com.coffeepay.specification;

import com.coffeepay.model.Address_;
import com.coffeepay.model.Role;
import com.coffeepay.model.Role_;
import org.springframework.data.jpa.domain.Specification;

import static util.DataGeneral.PERCENT_STRING;
import static util.DataGeneral.ROLE_DTO_CLASS;

public class RoleSpecification {
    public static Specification<Role> likeName(String name) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Role_.NAME),
                        new StringBuffer(PERCENT_STRING)
                                .append(name)
                                .append(PERCENT_STRING)
                                .toString()));
    }
}
