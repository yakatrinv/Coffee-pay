package com.coffeepay.specification;

import com.coffeepay.model.Address_;
import com.coffeepay.model.Machine;
import com.coffeepay.model.Machine_;
import com.coffeepay.model.ModelMachine_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import static util.DataGeneral.PERCENT_STRING;

public class MachineSpecification {
    public static Specification<Machine> likeSerialNumber(String serialNumber) {
        return StringUtils.isBlank(serialNumber) ? null :
                ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get(Machine_.SERIAL_NUMBER),
                                PERCENT_STRING +
                                        serialNumber +
                                        PERCENT_STRING));
    }

    public static Specification<Machine> likeBrand(String brand) {
        return StringUtils.isBlank(brand) ? null :
                ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get(Machine_.MODEL).get(ModelMachine_.BRAND),
                                PERCENT_STRING +
                                        brand +
                                        PERCENT_STRING));
    }

    public static Specification<Machine> likeModel(String nameModel) {
        return StringUtils.isBlank(nameModel) ? null :
                ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get(Machine_.MODEL).get(ModelMachine_.NAME_MODEL),
                                PERCENT_STRING +
                                        nameModel +
                                        PERCENT_STRING));
    }

    public static Specification<Machine> likeCity(String city) {
        return StringUtils.isBlank(city) ? null :
                ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get(Machine_.ADDRESS).get(Address_.CITY),
                                PERCENT_STRING +
                                        city +
                                        PERCENT_STRING));
    }

    public static Specification<Machine> likeStreet(String street) {
        return StringUtils.isBlank(street) ? null :
                ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get(Machine_.ADDRESS).get(Address_.STREET),
                                PERCENT_STRING +
                                        street +
                                        PERCENT_STRING));
    }

}
