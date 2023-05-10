package com.coffeepay.conrtroller;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.RoleDto;
import com.coffeepay.dto.UserDto;
import com.coffeepay.security.SecurityService;
import com.coffeepay.service.ICustomerService;
import com.coffeepay.service.IRoleService;
import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

import static util.DataGeneral.CUSTOMER;
import static util.DataMessages.MESSAGE_PASSWORD_NOT_EQUALS;
import static util.DataMessages.MESSAGE_USER_EXISTS;
import static util.DataMessages.MESSAGE_USER_EXISTS_ADD_ROLE;
import static util.DataMessages.VALID_CUSTOMER;
import static util.DataMessages.VALID_EQUALS_CONFIRM_PASSWORD;
import static util.DataMessages.VALID_USERNAME;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_USERNAME;
import static util.DataViews.MODEL_CUSTOMER;
import static util.DataViews.PAGE_PREV_URL;
import static util.DataViews.PAGE_PROFILE;
import static util.DataViews.PAGE_REDIRECT_APP;
import static util.DataViews.PAGE_REGISTRATION;
import static util.DataViews.URL_CUSTOMER;
import static util.DataViews.URL_MAIN;
import static util.DataViews.URL_NEW_CUSTOMER;
import static util.DataViews.URL_PROFILE;
import static util.DataViews.URL_UPDATE_PROFILE;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final SecurityService securityService;

    @GetMapping(URL_NEW_CUSTOMER)
    public String getRegistrationPage(Model model,
                                      @RequestHeader(HttpHeaders.REFERER) String prevURL) {
        model.addAttribute(MODEL_CUSTOMER, CustomerDto.builder()
                .user(new UserDto())
                .build());
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_MAIN : prevURL);

        return PAGE_REGISTRATION;
    }

    @PostMapping(URL_CUSTOMER)
    public String registration(
            @ModelAttribute(MODEL_CUSTOMER) @Valid CustomerDto customerDto,
            BindingResult bindingResult) {

        validateCustomer(customerDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return PAGE_REGISTRATION;
        }

        CustomerDto newCustomer = customerService.save(customerDto);
        if (newCustomer.getUser() != null) {
            securityService.autoLogin(
                    newCustomer.getUser().getUsername(),
                    newCustomer.getUser().getConfirmPassword());

        }
        return PAGE_REDIRECT_APP;
    }

    @GetMapping(URL_PROFILE)
    public String getProfilePage(Model model,
                                 @PathVariable(ATTR_USERNAME) String username,
                                 @RequestHeader(HttpHeaders.REFERER) String prevURL) {
        model.addAttribute(MODEL_CUSTOMER, customerService.findByUsername(username));
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_MAIN : prevURL);

        return PAGE_PROFILE;
    }

    @PatchMapping(URL_UPDATE_PROFILE)
    public String updateProfile(@PathVariable(ATTR_ID) Long id,
                                @ModelAttribute(MODEL_CUSTOMER) @Valid CustomerDto customerDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_PROFILE;
        }

        CustomerDto findCustomer = customerService.findById(id);
        if (findCustomer != null) {
            findCustomer.setName(customerDto.getName());
            findCustomer.setSurname(customerDto.getSurname());
            findCustomer.setPhone(customerDto.getPhone());
            findCustomer.setEmail(customerDto.getEmail());

            customerService.update(findCustomer);
        }

        return PAGE_REDIRECT_APP;

    }

    private void validateCustomer(CustomerDto customerDto, BindingResult bindingResult) {
        if (customerDto.getUser() != null) {
            String password = customerDto.getUser().getPassword();
            String confirmPassword = customerDto.getUser().getConfirmPassword();
            if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)) {
                if (!password.equals(confirmPassword)) {
                    bindingResult.addError(new FieldError(VALID_CUSTOMER, VALID_EQUALS_CONFIRM_PASSWORD,
                            MESSAGE_PASSWORD_NOT_EQUALS));
                }
            }
        }


        if (customerDto.getUser() != null
                && StringUtils.isNotBlank(customerDto.getUser().getUsername())) {
            UserDto userDto = userService.findByUserName(customerDto.getUser().getUsername());

            if (userDto != null) {
                RoleDto roleDto = roleService.findByName(CUSTOMER);
                if (userDto.getRoles().contains(roleDto)) {
                    //пользователь с такой ролью уже создан
                    bindingResult.addError(new FieldError(VALID_CUSTOMER, VALID_USERNAME,
                            MESSAGE_USER_EXISTS));
                } else {
                    //есть пользователь, добавлена роль, данные корректировать нельзя
                    bindingResult.addError(new FieldError(VALID_CUSTOMER, VALID_USERNAME,
                            MESSAGE_USER_EXISTS_ADD_ROLE));
                }
            }
        }
    }
}
