package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.io.Serializable;

import static util.DataMessages.MESSAGE_ERROR_MIN_DISCOUNT_PERCENT;
import static util.DataMessages.MESSAGE_ERROR_MIN_DISCOUNT_SUM;
import static util.DataMessages.MIN_NUMBER;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto implements Serializable {
    private Integer id;

    @Min(value = MIN_NUMBER, message = MESSAGE_ERROR_MIN_DISCOUNT_SUM)
    private int summ;

    @Min(value = MIN_NUMBER, message = MESSAGE_ERROR_MIN_DISCOUNT_PERCENT)
    private int percent;
}
