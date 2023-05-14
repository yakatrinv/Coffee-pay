package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static util.DataMessages.MAX_LENGTH_SERIAL_NUMBER;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_SERIAl_NUMBER;
import static util.DataMessages.MIN_LENGTH_SERIAL_NUMBER;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineDto implements Serializable {
    private Long id;

    @Size(min = MIN_LENGTH_SERIAL_NUMBER,
            max = MAX_LENGTH_SERIAL_NUMBER,
            message = MESSAGE_ERROR_LENGTH_SERIAl_NUMBER)
    private String serialNumber;

    @ToString.Exclude
    private AddressDto address;

    @ToString.Exclude
    private ModelMachineDto model;

    @ToString.Exclude
    private Set<ProductDto> products;
}
