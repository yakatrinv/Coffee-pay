package com.coffeepay.service.impl;

import com.coffeepay.dto.UserDto;
import com.coffeepay.repository.UserRepository;
import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static util.DataGeneral.USER_CLASS;
import static util.DataGeneral.USER_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void save(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(modelMapper.map(userDto, USER_CLASS));
    }

    @Override
    public UserDto findByUserName(String username) {
        return Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                .map(user -> modelMapper.map(user,USER_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public UserDto findByUsernameAndRolesIs(String username,String role) {
        return userRepository.findByUsernameAndRolesIs(username,role)
                .map(user -> modelMapper.map(user,USER_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public boolean isPasswordValid(UserDto userDto, String currentPassword) {
        return bCryptPasswordEncoder.matches(currentPassword, userDto.getPassword());
    }

    @Override
    public void update(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(modelMapper.map(userDto, USER_CLASS));
    }
}
