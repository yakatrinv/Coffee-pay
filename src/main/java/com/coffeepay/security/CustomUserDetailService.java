package com.coffeepay.security;

import com.coffeepay.model.User;
import com.coffeepay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static util.DataGeneral.USER_DTO_CLASS;
import static util.DataMessages.MESSAGE_USER_DOES_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(MESSAGE_USER_DOES_NOT_EXISTS)
                );

        return new CustomUserDetails(modelMapper.map(user, USER_DTO_CLASS));
    }
}
