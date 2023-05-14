package com.coffeepay.conrtroller;

import com.coffeepay.dto.TypePaymentDto;
import com.coffeepay.service.ITypePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static util.DataViews.ADD_AFTER_TYPE_PAYMENTS_PAGE;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_TYPE_PAYMENT;
import static util.DataViews.ATTR_TYPE_PAYMENTS_LIST;
import static util.DataViews.PAGE_ADD_TYPE_PAYMENT;
import static util.DataViews.PAGE_EDIT_TYPE_PAYMENT;
import static util.DataViews.PAGE_LIST_TYPE_PAYMENTS;
import static util.DataViews.PAGE_REDIRECT_LIST_TYPE_PAYMENTS;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_TYPE_PAYMENTS_PAGE)
public class TypePaymentController {
    private final ITypePaymentService typePaymentService;

    @GetMapping
    public String getTypePayments(Model model) {
        model.addAttribute(ATTR_TYPE_PAYMENTS_LIST, typePaymentService.getAll());

        return PAGE_LIST_TYPE_PAYMENTS;
    }

    @GetMapping(URL_NEW)
    public String newTypePayment(Model model) {
        model.addAttribute(ATTR_TYPE_PAYMENT, new TypePaymentDto());

        return PAGE_ADD_TYPE_PAYMENT;
    }

    @PostMapping
    public String createTypePayment(@ModelAttribute(ATTR_TYPE_PAYMENT) @Valid TypePaymentDto typePaymentDto,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_ADD_TYPE_PAYMENT;
        }
        typePaymentService.save(typePaymentDto);

        return PAGE_REDIRECT_LIST_TYPE_PAYMENTS;
    }

    @GetMapping(URL_EDIT)
    public String editTypePayment(Model model,
                                  @PathVariable(ATTR_ID) Integer id) {

        model.addAttribute(ATTR_TYPE_PAYMENT, typePaymentService.findById(id));

        return PAGE_EDIT_TYPE_PAYMENT;
    }

    @PatchMapping(URL_UPDATE)
    public String updateTypePayment(@ModelAttribute(ATTR_TYPE_PAYMENT) @Valid TypePaymentDto typePaymentDto,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_TYPE_PAYMENT;
        }

        typePaymentService.save(typePaymentDto);

        return PAGE_REDIRECT_LIST_TYPE_PAYMENTS;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteTypePayment(@PathVariable(ATTR_ID) Integer id) {
        typePaymentService.deleteById(id);

        return PAGE_REDIRECT_LIST_TYPE_PAYMENTS;
    }
}
