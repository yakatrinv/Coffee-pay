package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

import static util.DataMessages.MESSAGE_ERROR_MIN_PRODUCT_PRICE;
import static util.DataMessages.MESSAGE_ERROR_MIN_PURCHASE_SUM;
import static util.DataMessages.MIN_NUMBER;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto implements Serializable {
    private Long id;

    private CustomerDto customer;

    @ToString.Exclude
    private MachineDto machine;

    @ToString.Exclude
    private ProductDto product;

    @Min(value = MIN_NUMBER,message = MESSAGE_ERROR_MIN_PRODUCT_PRICE)
    private float price;

    @ToString.Exclude
    private DiscountDto discount;

    @Min(value = MIN_NUMBER,message = MESSAGE_ERROR_MIN_PURCHASE_SUM)
    private float summ;

    private CreditCardDto creditCard;

    private TypePaymentDto typePayment;

    private LocalDateTime createDate;
}
