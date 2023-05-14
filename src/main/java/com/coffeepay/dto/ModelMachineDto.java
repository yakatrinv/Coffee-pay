package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.io.Serializable;

import static util.DataMessages.MAX_LENGTH_BRAND;
import static util.DataMessages.MAX_LENGTH_CITY;
import static util.DataMessages.MAX_LENGTH_MODEl;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_BRAND;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_CITY;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_MODEL;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModelMachineDto implements Serializable {
    private Long id;

    @Size(max = MAX_LENGTH_BRAND, message = MESSAGE_ERROR_LENGTH_BRAND)
    private String brand;

    @Size(max = MAX_LENGTH_MODEl, message = MESSAGE_ERROR_LENGTH_MODEL)
    private String nameModel;
}
