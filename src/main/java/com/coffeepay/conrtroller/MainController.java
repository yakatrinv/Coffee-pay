package com.coffeepay.conrtroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping
    public String getHomePage() {
        return "/main/index";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {

        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "/main/login";
    }

}
