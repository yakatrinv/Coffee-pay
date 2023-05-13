package com.coffeepay.conrtroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static util.DataViews.PAGE_INDEX;
import static util.DataViews.PAGE_MAIN;
import static util.DataViews.URL_APP;

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
