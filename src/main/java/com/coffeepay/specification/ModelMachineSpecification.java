package com.coffeepay.specification;

import com.coffeepay.model.ModelMachine;
import com.coffeepay.model.ModelMachine_;
import org.springframework.data.jpa.domain.Specification;

import static util.DataGeneral.PERCENT_STRING;

public class ModelMachineSpecification {
    public static Specification<ModelMachine> likeBrand(String brand) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(ModelMachine_.BRAND),
                        PERCENT_STRING +
                                brand +
                                PERCENT_STRING));
    }

    public static Specification<ModelMachine> likeModel(String model) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get(ModelMachine_.nameModel),
                        PERCENT_STRING +
                                model +
                                PERCENT_STRING));
    }
}
