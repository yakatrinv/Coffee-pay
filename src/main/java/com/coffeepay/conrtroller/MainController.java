package com.coffeepay.conrtroller;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static util.DataViews.MODEL_CUSTOMER;
import static util.DataViews.PAGE_INDEX;
import static util.DataViews.PAGE_MAIN;
import static util.DataViews.PAGE_REGISTRATION;
import static util.DataViews.URL_APP;
import static util.DataViews.URL_REGISTRATION;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping
    public String getIndexPage() {
        return PAGE_INDEX;
    }

    @GetMapping(URL_APP)
    public String getHomePage() {
        return PAGE_MAIN;
    }
}
