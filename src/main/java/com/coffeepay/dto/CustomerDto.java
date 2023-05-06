package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static util.DataMessages.MAX_NAME_SURNAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_NAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_SURNAME;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerDto implements Serializable {
    private Long id;

    @EqualsAndHashCode.Include
    @Size(max = MAX_NAME_SURNAME, message = MESSAGE_ERROR_LENGTH_NAME)
    private String name;

    @EqualsAndHashCode.Include
    @Size(max = MAX_NAME_SURNAME, message = MESSAGE_ERROR_LENGTH_SURNAME)
    private String surname;

    @EqualsAndHashCode.Include
    private String phone;

    @EqualsAndHashCode.Include
    @Email
    private String email;

    @ToString.Exclude
    @Valid
    private UserDto user;
}