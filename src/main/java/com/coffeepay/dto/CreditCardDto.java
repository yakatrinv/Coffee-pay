
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

import static util.DataMessages.LENGTH_CARD_NUMBER;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_CREDIT_CARD;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreditCardDto implements Serializable {
    private Long id;

    @ToString.Include
    @Size(min = LENGTH_CARD_NUMBER, max = LENGTH_CARD_NUMBER,
            message = MESSAGE_ERROR_LENGTH_CREDIT_CARD)
    private String number;

    private CustomerDto customer;
}
