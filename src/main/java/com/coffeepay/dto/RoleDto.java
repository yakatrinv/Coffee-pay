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

import static util.DataMessages.MAX_ROLE_NAME;
import static util.DataMessages.MESSAGE_ERROR_LENGTH_ROLE_NAME;
import static util.DataMessages.MIN_ROLE_NAME;


@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {
    private Integer id;
    @Size(min = MIN_ROLE_NAME, max = MAX_ROLE_NAME,
            message = MESSAGE_ERROR_LENGTH_ROLE_NAME)
    private String name;
}
