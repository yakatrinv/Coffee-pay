package com.coffeepay.conrtroller;

import com.coffeepay.dto.UserDto;
import com.coffeepay.model.User;
import com.coffeepay.security.SecurityService;
import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final IUserService userService;
    private final SecurityService securityService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getIndexPage() {
        return "/index";
    }

    @GetMapping("/api")
    public String getHomePage() {
        return "/views/index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
//        model.addAttribute("customer", new CustomerDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto,
                               BindingResult bindingResult) {
        //добавить валидацию
        if (!userDto.getConfirmPassword().equals(userDto.getPassword())) {
            return "registration";
        }
        userService.save(modelMapper.map(userDto, User.class));
        securityService.autoLogin(userDto.getUsername(), userDto.getConfirmPassword());
        return "redirect:/api";
    }

}
