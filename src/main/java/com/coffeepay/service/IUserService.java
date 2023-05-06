package com.coffeepay.service;

import com.coffeepay.dto.UserDto;

public interface IUserService {
    void save(UserDto user);

    UserDto findByUserName(String username);

    boolean isPasswordValid(UserDto userDto, String currentPassword);

    void update(UserDto userDto);
}
