package com.coffeepay.conrtroller;

import com.coffeepay.dto.CreditCardDto;
import com.coffeepay.dto.CustomerDto;
import com.coffeepay.service.ICreditCardService;
import com.coffeepay.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.List;

import static util.DataViews.ATTR_CREDIT_CARD;
import static util.DataViews.ATTR_CREDIT_CARDS;
import static util.DataViews.ATTR_CUSTOMER;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_USERNAME;
import static util.DataViews.PAGE_ADD_CUSTOMER_CREDIT_CARD;
import static util.DataViews.PAGE_CUSTOMER_CREDIT_CARDS;
import static util.DataViews.PAGE_EDIT_CUSTOMER_CREDIT_CARD;
import static util.DataViews.PAGE_PREV_URL;
import static util.DataViews.PAGE_REDIRECT_CUSTOMER_CREDIT_CARD;
import static util.DataViews.URL_CUSTOMER_CREDIT_CARDS;
import static util.DataViews.URL_DELETE_CUSTOMER_CREDIT_CARDS;
import static util.DataViews.URL_EDIT_CUSTOMER_CREDIT_CARDS;
import static util.DataViews.URL_MAIN;
import static util.DataViews.URL_NEW_CUSTOMER_CREDIT_CARDS;
import static util.DataViews.URL_UPDATE_CUSTOMER_CREDIT_CARDS;

@Controller
@RequiredArgsConstructor
public class CreditCardController {

    private final ICustomerService customerService;
    private final ICreditCardService creditCardService;

    @GetMapping(URL_CUSTOMER_CREDIT_CARDS)
    public String getCreditCards(Model model,
                                 @PathVariable(ATTR_USERNAME) String username) {
        CustomerDto customer = customerService.findByUsername(username);
        List<CreditCardDto> creditCards = creditCardService.findByCustomer(customer);

        model.addAttribute(ATTR_CREDIT_CARDS, creditCards);
        model.addAttribute(ATTR_CUSTOMER, customer);
        return PAGE_CUSTOMER_CREDIT_CARDS;
    }

    @GetMapping(URL_NEW_CUSTOMER_CREDIT_CARDS)
    public String addCreditCard(@PathVariable String username,
                                Model model,
                                @RequestHeader(HttpHeaders.REFERER) String prevURL) {

        CustomerDto customer = customerService.findByUsername(username);

        model.addAttribute(ATTR_CUSTOMER, customer);
        model.addAttribute(ATTR_CREDIT_CARD, CreditCardDto.builder()
                .customer(customer)
                .build());
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_MAIN : prevURL);

        return PAGE_ADD_CUSTOMER_CREDIT_CARD;
    }

    @PostMapping(URL_CUSTOMER_CREDIT_CARDS)
    public String saveCreditCard(@ModelAttribute(ATTR_CREDIT_CARD) @Valid CreditCardDto creditCardDto,
                                 BindingResult bindingResult,
                                 @PathVariable String username,
                                 Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(ATTR_CUSTOMER, customerService.findByUsername(username));
            return PAGE_ADD_CUSTOMER_CREDIT_CARD;
        }
        CustomerDto customer = customerService.findByUsername(username);
        if (customer != null) {
            creditCardDto.setCustomer(customer);
            creditCardService.save(creditCardDto);
        }

        return PAGE_REDIRECT_CUSTOMER_CREDIT_CARD;
    }

    @GetMapping(URL_EDIT_CUSTOMER_CREDIT_CARDS)
    public String editCreditCard(@PathVariable String username,
                                 @PathVariable(ATTR_ID) long id,
                                 Model model,
                                 @RequestHeader(HttpHeaders.REFERER) String prevURL) {

        CustomerDto customer = customerService.findByUsername(username);

        model.addAttribute(ATTR_CUSTOMER, customer);
        model.addAttribute(ATTR_CREDIT_CARD, creditCardService.fidById(id));
        model.addAttribute(PAGE_PREV_URL, StringUtils.isBlank(prevURL) ? URL_MAIN : prevURL);

        return PAGE_EDIT_CUSTOMER_CREDIT_CARD;
    }

    @PatchMapping(URL_UPDATE_CUSTOMER_CREDIT_CARDS)
    public String updateCreditCard(@ModelAttribute(ATTR_CREDIT_CARD) @Valid CreditCardDto creditCardDto,
                                   BindingResult bindingResult,
                                   @PathVariable String username,
                                   @PathVariable(ATTR_ID) long id,
                                   Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(ATTR_CUSTOMER, customerService.findByUsername(username));
            return PAGE_EDIT_CUSTOMER_CREDIT_CARD;
        }
        CustomerDto customer = customerService.findByUsername(username);
        if (customer != null) {
            creditCardDto.setCustomer(customer);
            creditCardService.save(creditCardDto);
        }

        return PAGE_REDIRECT_CUSTOMER_CREDIT_CARD;
    }

    @DeleteMapping(URL_DELETE_CUSTOMER_CREDIT_CARDS)
    public String deleteAddress(@PathVariable(ATTR_USERNAME) String username,
                                @PathVariable(ATTR_ID) long id) {

        creditCardService.deleteById(id);
        return PAGE_REDIRECT_CUSTOMER_CREDIT_CARD;
    }
}
