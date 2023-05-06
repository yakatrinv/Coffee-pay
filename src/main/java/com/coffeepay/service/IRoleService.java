package com.coffeepay.service;

import com.coffeepay.dto.RoleDto;

public interface IRoleService {
    RoleDto findByName(String name);
}
