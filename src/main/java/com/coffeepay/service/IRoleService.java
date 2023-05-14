package com.coffeepay.service;

import com.coffeepay.dto.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    Page<RoleDto> findAll(String name, Pageable pageable);

    RoleDto findByName(String name);

    void save(RoleDto roleDto);

    void update(RoleDto roleDto);

    RoleDto findById(Integer id);

    void deleteById(Integer id);
}
