package com.coffeepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    @ToString.Exclude
    private CustomerDto customerDto;

    private Set<RoleDto> roles;
}
