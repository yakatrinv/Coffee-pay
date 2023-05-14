package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.io.Serializable;

import static util.DataMessages.MAX_LENGTH_CITY;
import static util.DataMessages.MAX_LENGTH_STREET;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_CITY;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_STREET;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {

    private Long id;

    @Size(max = MAX_LENGTH_CITY, message = MESSAGE_ERROR_LENGTH_CITY)
    private String city;

    @Size(max = MAX_LENGTH_STREET, message = MESSAGE_ERROR_LENGTH_STREET)
    private String street;
}
