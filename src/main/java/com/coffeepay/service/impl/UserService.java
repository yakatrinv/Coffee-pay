package com.coffeepay.service.impl;

import com.coffeepay.dto.UserDto;
import com.coffeepay.repository.UserRepository;
import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.DataGeneral.USER_CLASS;
import static util.DataGeneral.USER_DTO_CLASS;
import static util.DataMessages.MESSAGE_ERROR_PASSWORD;
import static util.DataMessages.MESSAGE_PASSWORD_NOT_EQUALS;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Override
    public void save(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(modelMapper.map(userDto, USER_CLASS));
    }

    @Override
    public UserDto findByUserName(String username) {
        return Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                .map(user -> modelMapper.map(user, USER_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public UserDto findByUsernameAndRolesIs(String username, String role) {
        return userRepository.findByUsernameAndRolesIs(username, role)
                .map(user -> modelMapper.map(user, USER_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public boolean isPasswordValid(UserDto userDto, String currentPassword) {
        return bCryptPasswordEncoder.matches(currentPassword, userDto.getPassword());
    }

    @Override
    public List<String> updatePassword(String username,
                                       String password,
                                       String newPassword,
                                       String confirmPassword) {


        List<String> errors = new ArrayList<>();

        UserDto userDto = Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                .map(user -> modelMapper.map(user, USER_DTO_CLASS))
                .orElse(null);

        if (userDto != null) {
            if (!isPasswordValid(userDto, password)) {
                errors.add(messageSource.getMessage(
                        MESSAGE_ERROR_PASSWORD,
                        new Object[]{},
                        LocaleContextHolder.getLocale()));
            }
        }

        if (StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(confirmPassword)) {
            if (!newPassword.equals(confirmPassword)) {
                errors.add(messageSource.getMessage(
                        MESSAGE_PASSWORD_NOT_EQUALS,
                        new Object[]{},
                        LocaleContextHolder.getLocale()));
            }
        }

        if (userDto != null && errors.isEmpty()) {
            userDto.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(modelMapper.map(userDto, USER_CLASS));
        }

        return errors;

    }
}
