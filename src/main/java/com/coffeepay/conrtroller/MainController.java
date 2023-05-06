package com.coffeepay.conrtroller;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.RoleDto;
import com.coffeepay.dto.UserDto;
import com.coffeepay.security.CustomUserDetails;
import com.coffeepay.security.SecurityService;
import com.coffeepay.service.ICustomerService;
import com.coffeepay.service.IRoleService;
import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static util.DataGeneral.CUSTOMER;
import static util.DataMessages.MESSAGE_ERROR_PASSWORD;
import static util.DataMessages.MESSAGE_PASSWORD_NOT_EQUALS;
import static util.DataMessages.MESSAGE_USER_EXISTS;
import static util.DataMessages.MESSAGE_USER_EXISTS_ADD_ROLE;
import static util.DataMessages.VALID_CUSTOMER;
import static util.DataMessages.VALID_EQUALS_CONFIRM_PASSWORD;
import static util.DataMessages.VALID_USERNAME;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_USERNAME;
import static util.DataViews.MODEL_CUSTOMER;
import static util.DataViews.MODEL_ERRORS;
import static util.DataViews.PAGE_CHANGE_PASSWORD;
import static util.DataViews.PAGE_INDEX;
import static util.DataViews.PAGE_MAIN;
import static util.DataViews.PAGE_PROFILE;
import static util.DataViews.PAGE_REDIRECT_APP;
import static util.DataViews.PAGE_REGISTRATION;
import static util.DataViews.PARAM_CONFIRM_PASSWORD;
import static util.DataViews.PARAM_NEW_PASSWORD;
import static util.DataViews.PARAM_PASSWORD;
import static util.DataViews.URL_APP;
import static util.DataViews.URL_CHANGE_PASSWORD;
import static util.DataViews.URL_EDIT_PROFILE;
import static util.DataViews.URL_REGISTRATION;
import static util.DataViews.URL_UPDATE_PROFILE;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ICustomerService customerService;
    private final IUserService userService;
    private final IRoleService roleService;
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
    public String getRegistrationPage(Model model) {
        model.addAttribute(MODEL_CUSTOMER, CustomerDto.builder()
                .user(new UserDto())
                .build());

        return PAGE_REGISTRATION;
    }

    @PostMapping
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

    @GetMapping(URL_CHANGE_PASSWORD)
    public String getChangePasswordPage() {
        return PAGE_CHANGE_PASSWORD;
    }

    @PostMapping(URL_CHANGE_PASSWORD)
    public String saveNewPassword(Model model,
                                  @RequestParam(value = PARAM_PASSWORD, defaultValue = "") String password,
                                  @RequestParam(value = PARAM_NEW_PASSWORD, defaultValue = "") String newPassword,
                                  @RequestParam(value = PARAM_CONFIRM_PASSWORD, defaultValue = "") String confirmPassword) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());
        UserDto userDto = userService.findByUserName(userDetails.getUsername());

        List<String> errors = validatePassword(password, newPassword, confirmPassword, userDto);

        model.addAttribute(MODEL_ERRORS, errors);
        if (errors.size() > 0) {
            return PAGE_CHANGE_PASSWORD;
        }

        userDto.setPassword(newPassword);
        userDto.setConfirmPassword(confirmPassword);
        userService.update(userDto);

        return PAGE_REDIRECT_APP;
    }

    @GetMapping(URL_EDIT_PROFILE)
    public String getProfilePage(Model model,
                                 @PathVariable(ATTR_USERNAME) String username) {
        model.addAttribute(MODEL_CUSTOMER, customerService.findByUsername(username));

        return PAGE_PROFILE;
    }

    @PostMapping(URL_UPDATE_PROFILE)
    public String updateProfile(@PathVariable(ATTR_ID) Long id,
                                @ModelAttribute(MODEL_CUSTOMER) @Valid CustomerDto customerDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_PROFILE;
        }

        CustomerDto findCustomer = customerService.findById(id);
        findCustomer.setName(customerDto.getName());
        findCustomer.setSurname(customerDto.getSurname());
        findCustomer.setPhone(customerDto.getPhone());
        findCustomer.setEmail(customerDto.getEmail());

        customerService.update(findCustomer);

        return PAGE_REDIRECT_APP;

    }

    private List<String> validatePassword(String password,
                                          String newPassword,
                                          String confirmPassword,
                                          UserDto userDto) {

        List<String> errors = new ArrayList<>();
        boolean passwordValid = userService.isPasswordValid(userDto, password);
        if (!passwordValid) {
            errors.add(MESSAGE_ERROR_PASSWORD);
        }

        if (StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(confirmPassword)) {
            if (!newPassword.equals(confirmPassword)) {
                errors.add(MESSAGE_PASSWORD_NOT_EQUALS);
            }
        }
        return errors;
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
