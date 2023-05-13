package com.coffeepay.service.impl;

import com.coffeepay.dto.RoleDto;
import com.coffeepay.repository.RoleRepository;
import com.coffeepay.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static util.DataGeneral.ROLE_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoleDto findByName(String name) {
        return modelMapper.map(
                roleRepository.findByName(name),
                ROLE_DTO_CLASS);
    }
}
