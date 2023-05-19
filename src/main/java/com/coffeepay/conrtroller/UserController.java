package com.coffeepay.conrtroller;

import com.coffeepay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static util.DataViews.ATTR_USERNAME;
import static util.DataViews.MODEL_ERRORS;
import static util.DataViews.PAGE_CHANGE_PASSWORD;
import static util.DataViews.PAGE_PREV_URL;
import static util.DataViews.PAGE_REDIRECT_APP;
import static util.DataViews.PARAM_CONFIRM_PASSWORD;
import static util.DataViews.PARAM_NEW_PASSWORD;
import static util.DataViews.PARAM_PASSWORD;
import static util.DataViews.URL_APP;
import static util.DataViews.URL_EDIT_PASSWORD;
import static util.DataViews.URL_SAVE_PASSWORD;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping(URL_EDIT_PASSWORD)
    public String getChangePasswordPage(Model model,
                                        @PathVariable String username,
                                        @RequestParam(value = PAGE_PREV_URL, defaultValue = "") String prevURL) {
        model.addAttribute(ATTR_USERNAME, username);
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_APP : prevURL);
        return PAGE_CHANGE_PASSWORD;
    }

    @PostMapping(URL_SAVE_PASSWORD)
    public String saveNewPassword(Model model,
                                  @PathVariable String username,
                                  @RequestParam(value = PARAM_PASSWORD, defaultValue = "") String password,
                                  @RequestParam(value = PARAM_NEW_PASSWORD, defaultValue = "") String newPassword,
                                  @RequestParam(value = PARAM_CONFIRM_PASSWORD, defaultValue = "") String confirmPassword,
                                  @RequestParam(value = PAGE_PREV_URL, defaultValue = "") String prevURL) {
        List<String> errors = userService.updatePassword(username, password, newPassword, confirmPassword);
        if (errors.size() > 0) {
            model.addAttribute(MODEL_ERRORS, errors);
            model.addAttribute(PAGE_PREV_URL, prevURL);
            return PAGE_CHANGE_PASSWORD;
        }

        return PAGE_REDIRECT_APP;
    }
}