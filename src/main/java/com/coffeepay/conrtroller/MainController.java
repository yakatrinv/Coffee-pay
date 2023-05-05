package com.coffeepay.conrtroller;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.UserDto;
import com.coffeepay.security.SecurityService;
import com.coffeepay.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static util.DataViews.MODEL_CUSTOMER;
import static util.DataViews.PAGE_INDEX;
import static util.DataViews.PAGE_MAIN;
import static util.DataViews.PAGE_REDIRECT_APP;
import static util.DataViews.PAGE_REGISTRATION;
import static util.DataViews.URL_APP;
import static util.DataViews.URL_REGISTRATION;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ICustomerService customerService;
    private final SecurityService securityService;

    @GetMapping
    public String getIndexPage() {
        return PAGE_INDEX;
    }

    @GetMapping(URL_APP)
    public String getHomePage() {
        return PAGE_MAIN;
    }

    @GetMapping(URL_REGISTRATION)
    public String registration(Model model) {
        model.addAttribute(MODEL_CUSTOMER, CustomerDto.builder()
                .user(new UserDto())
                .build());

        return PAGE_REGISTRATION;
    }

    @PostMapping(URL_REGISTRATION)
    public String registration(
            @ModelAttribute(MODEL_CUSTOMER) CustomerDto customerDto,
            BindingResult bindingResult) {
        //добавить валидацию
        CustomerDto newCustomer = customerService.save(customerDto);
        securityService.autoLogin(
                newCustomer.getUser().getUsername(),
                newCustomer.getUser().getConfirmPassword());

        return PAGE_REDIRECT_APP;
    }
}
