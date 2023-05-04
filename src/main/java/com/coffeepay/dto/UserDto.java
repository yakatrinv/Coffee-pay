package com.coffeepay.dto;

import com.coffeepay.model.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDto extends DataEntity implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;

    private Set<RoleDto> roles;
}
