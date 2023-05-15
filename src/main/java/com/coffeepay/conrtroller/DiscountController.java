package com.coffeepay.conrtroller;

import com.coffeepay.dto.DiscountDto;
import com.coffeepay.service.IDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataViews.ADD_AFTER_DISCOUNTS_PAGE;
import static util.DataViews.ADD_AFTER_PRODUCTS_PAGE;
import static util.DataViews.ATTR_DISCOUNT;
import static util.DataViews.ATTR_DISCOUNTS_LIST;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_PRODUCT;
import static util.DataViews.ATTR_SEARCH_DISCOUNT_MAX_PERCENT;
import static util.DataViews.ATTR_SEARCH_DISCOUNT_MAX_SUM;
import static util.DataViews.ATTR_SEARCH_DISCOUNT_MIN_PERCENT;
import static util.DataViews.ATTR_SEARCH_DISCOUNT_MIN_SUM;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_DISCOUNT;
import static util.DataViews.PAGE_EDIT_DISCOUNT;
import static util.DataViews.PAGE_LIST_DISCOUNTS;
import static util.DataViews.PAGE_REDIRECT_LIST_DISCOUNTS;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_DISCOUNTS_PAGE)
public class DiscountController {
    private final IDiscountService discountService;

    @GetMapping
    public String getDiscounts(Model model,
                               @RequestParam(value = ATTR_SEARCH_DISCOUNT_MIN_SUM, defaultValue = "0") int minSum,
                               @RequestParam(value = ATTR_SEARCH_DISCOUNT_MAX_SUM, defaultValue = "0") int maxSum,
                               @RequestParam(value = ATTR_SEARCH_DISCOUNT_MIN_PERCENT, defaultValue = "0") int minPercent,
                               @RequestParam(value = ATTR_SEARCH_DISCOUNT_MAX_PERCENT, defaultValue = "0") int maxPercent,
                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<DiscountDto> pageable = discountService.findAll(
                minSum,
                maxSum,
                minPercent,
                maxPercent,
                pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_DISCOUNTS_PAGE);
        model.addAttribute(ATTR_DISCOUNTS_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_DISCOUNT_MIN_PERCENT, minPercent);
        model.addAttribute(ATTR_SEARCH_DISCOUNT_MAX_PERCENT, maxPercent);
        model.addAttribute(ATTR_SEARCH_DISCOUNT_MIN_SUM, minSum);
        model.addAttribute(ATTR_SEARCH_DISCOUNT_MAX_SUM, maxSum);

        return PAGE_LIST_DISCOUNTS;
    }

    @GetMapping(URL_NEW)
    public String newDiscount(Model model) {
        model.addAttribute(ATTR_DISCOUNT, new DiscountDto());

        return PAGE_ADD_DISCOUNT;
    }

    @PostMapping
    public String createDiscount(@ModelAttribute(ATTR_DISCOUNT) @Valid DiscountDto discountDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_ADD_DISCOUNT;
        }
        discountService.save(discountDto);

        return PAGE_REDIRECT_LIST_DISCOUNTS;
    }

    @GetMapping(URL_EDIT)
    public String editDiscount(Model model,
                               @PathVariable(ATTR_ID) Integer id) {

        model.addAttribute(ATTR_DISCOUNT, discountService.findById(id));

        return PAGE_EDIT_DISCOUNT;
    }

    @PatchMapping(URL_UPDATE)
    public String updateDiscount(@ModelAttribute(ATTR_DISCOUNT) @Valid DiscountDto discountDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_DISCOUNT;
        }

        discountService.save(discountDto);

        return PAGE_REDIRECT_LIST_DISCOUNTS;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteDiscount(@PathVariable(ATTR_ID) Integer id) {

        discountService.deleteById(id);

        return PAGE_REDIRECT_LIST_DISCOUNTS;
    }

}
