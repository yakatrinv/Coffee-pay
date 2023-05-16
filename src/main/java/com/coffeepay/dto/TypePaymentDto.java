package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.io.Serializable;

import static util.DataMessages.MAX_NAME_SURNAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_TYPE_PAYMENT_NAME;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TypePaymentDto implements Serializable {
    private Integer id;

    @ToString.Include
    @Size(max = MAX_NAME_SURNAME, message = MESSAGE_ERROR_LENGTH_TYPE_PAYMENT_NAME)
    private String name;

    private Boolean useCreditCard;

    private Boolean usePhoneNumber;
}
