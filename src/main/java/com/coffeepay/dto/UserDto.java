package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static util.DataMessages.MAX_PASSWORD;
import static util.DataMessages.MAX_USERNAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_PASSWORD;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_USERNAME;
import static util.DataMessages.MESSAGE_ERROR_START_USERNAME;
import static util.DataMessages.MIN_PASSWORD;
import static util.DataMessages.MIN_USERNAME;
import static util.DataMessages.REG_SYMBOL;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDto implements Serializable {
    private Long id;

    @Size(min = MIN_USERNAME, max = MAX_USERNAME,
            message = MESSAGE_ERROR_LENGTH_USERNAME)
    @Pattern(regexp = REG_SYMBOL, message = MESSAGE_ERROR_START_USERNAME)
    private String username;

    @Size(min = MIN_PASSWORD, max = MAX_PASSWORD,
            message = MESSAGE_ERROR_LENGTH_PASSWORD)
    private String password;

    private String confirmPassword;

    private Set<RoleDto> roles;
}
