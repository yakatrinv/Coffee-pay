package com.coffeepay.service.impl;

import com.coffeepay.dto.RoleDto;
import com.coffeepay.model.Role;
import com.coffeepay.repository.RoleRepository;
import com.coffeepay.service.IRoleService;
import com.coffeepay.specification.RoleSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static util.DataGeneral.ROLE_CLASS;
import static util.DataGeneral.ROLE_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<RoleDto> findAll(String name, Pageable pageable) {
        Page<Role> rolesPage = roleRepository.findAll(RoleSpecification.likeName(name), pageable);

        return new PageImpl<>(
                rolesPage
                        .stream()
                        .map(role -> modelMapper.map(role, ROLE_DTO_CLASS))
                        .toList(),
                pageable,
                rolesPage.getTotalElements());
    }

    @Override
    public RoleDto findByName(String name) {
        return modelMapper.map(
                roleRepository.findByName(name),
                ROLE_DTO_CLASS);
    }

    @Override
    public void save(RoleDto roleDto) {
        roleRepository.save(modelMapper.map(roleDto, ROLE_CLASS));
    }

    @Override
    public void update(RoleDto roleDto) {
        roleRepository.save(modelMapper.map(roleDto, ROLE_CLASS));
    }

    @Override
    public RoleDto findById(Integer id) {
        return Optional.ofNullable(id)
                .map(roleRepository::findById)
                .map(role -> modelMapper.map(role, ROLE_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
