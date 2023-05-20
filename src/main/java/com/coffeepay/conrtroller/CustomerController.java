package com.coffeepay.conrtroller;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.UserDto;
import com.coffeepay.security.SecurityService;
import com.coffeepay.service.ICustomerService;
import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataGeneral.CUSTOMER;
import static util.DataMessages.MESSAGE_PASSWORD_NOT_EQUALS;
import static util.DataMessages.MESSAGE_USER_EXISTS;
import static util.DataMessages.VALID_CUSTOMER;
import static util.DataMessages.VALID_EQUALS_CONFIRM_PASSWORD;
import static util.DataMessages.VALID_USERNAME;
import static util.DataViews.ATTR_USERNAME;
import static util.DataViews.MODEL_CUSTOMER;
import static util.DataViews.PAGE_PREV_URL;
import static util.DataViews.PAGE_PROFILE;
import static util.DataViews.PAGE_REDIRECT_APP;
import static util.DataViews.PAGE_REGISTRATION;
import static util.DataViews.URL_APP;
import static util.DataViews.URL_CUSTOMER;
import static util.DataViews.URL_NEW_CUSTOMER;
import static util.DataViews.URL_PROFILE;
import static util.DataViews.URL_UPDATE_PROFILE;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;
    private final IUserService userService;
    private final SecurityService securityService;
    private final MessageSource messageSource;

    @GetMapping(URL_NEW_CUSTOMER)
    public String getRegistrationPage(Model model,
                                      @RequestParam(value = PAGE_PREV_URL, defaultValue = "") String prevURL) {
        model.addAttribute(MODEL_CUSTOMER, CustomerDto.builder()
                .user(new UserDto())
                .build());
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_APP : prevURL);

        return PAGE_REGISTRATION;
    }

    @PostMapping(URL_CUSTOMER)
    public String registration(Model model,
                               @ModelAttribute(MODEL_CUSTOMER) @Valid CustomerDto customerDto,
                               BindingResult bindingResult,
                               @RequestParam(value = PAGE_PREV_URL, defaultValue = "") String prevURL) {

        validateCustomer(customerDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_PREV_URL, prevURL);
            return PAGE_REGISTRATION;
        }

        customerService.save(customerDto);
        return PAGE_REDIRECT_APP;
    }

    @GetMapping(URL_PROFILE)
    public String getProfilePage(Model model,
                                 @PathVariable(ATTR_USERNAME) String username,
                                 @RequestParam(value = PAGE_PREV_URL, defaultValue = "") String prevURL) {
        model.addAttribute(MODEL_CUSTOMER, customerService.findByUsername(username));
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_APP : prevURL);

        return PAGE_PROFILE;
    }

    @PatchMapping(URL_UPDATE_PROFILE)
    public String updateProfile(@ModelAttribute(MODEL_CUSTOMER) @Valid CustomerDto customerDto,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam(value = PAGE_PREV_URL, defaultValue = "") String prevURL) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_PREV_URL, prevURL);
            return PAGE_PROFILE;
        }
        customerService.update(customerDto);
        return PAGE_REDIRECT_APP;
    }

    private void validateCustomer(CustomerDto customerDto, BindingResult bindingResult) {
        if (customerDto.getUser() != null) {
            String password = customerDto.getUser().getPassword();
            String confirmPassword = customerDto.getUser().getConfirmPassword();
            if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)) {
                if (!password.equals(confirmPassword)) {

                    String errorMessage = messageSource
                            .getMessage(MESSAGE_PASSWORD_NOT_EQUALS,
                                    new Object[]{},
                                    LocaleContextHolder.getLocale());

                    bindingResult.addError(new FieldError(VALID_CUSTOMER, VALID_EQUALS_CONFIRM_PASSWORD,
                            errorMessage));
                }
            }
        }

        if (customerDto.getUser() != null) {
            UserDto userDto = userService
                    .findByUsernameAndRolesIs(customerDto
                                    .getUser()
                                    .getUsername(),
                            CUSTOMER);

            if (userDto != null) {
                //пользователь с такой ролью уже создан
                String errorMessage = messageSource
                        .getMessage(MESSAGE_USER_EXISTS,
                                new Object[]{},
                                LocaleContextHolder.getLocale());

                bindingResult.addError(new FieldError(VALID_CUSTOMER, VALID_USERNAME,
                        errorMessage));
            }
        }
    }
}
