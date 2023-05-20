package com.coffeepay.service;

import com.coffeepay.dto.UserDto;

import java.util.List;

public interface IUserService {
    void save(UserDto user);

    UserDto findByUserName(String username);

    UserDto findByUsernameAndRolesIs(String username, String role);

    boolean isPasswordValid(UserDto userDto, String currentPassword);

    List<String> updatePassword(String username,
                                String password,
                                String newPassword,
                                String confirmPassword);
}
