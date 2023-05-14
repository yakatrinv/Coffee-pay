package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static util.DataMessages.MAX_NAME_SURNAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_CUSTOMER_NAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_PRODUCT_NAME;
import static util.DataMessages.MESSAGE_ERROR_MIN_PRODUCT_PRICE;
import static util.DataMessages.MIN_NUMBER;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id", "name", "price"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long id;

    @Size(max = MAX_NAME_SURNAME, message = MESSAGE_ERROR_LENGTH_PRODUCT_NAME)
    private String name;

    @Min(value = MIN_NUMBER,message = MESSAGE_ERROR_MIN_PRODUCT_PRICE)
    private float price;
}
