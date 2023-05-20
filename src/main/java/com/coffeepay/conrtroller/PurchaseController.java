package com.coffeepay.conrtroller;

import com.coffeepay.dto.PurchaseDto;
import com.coffeepay.service.IPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataMessages.MESSAGE_ERROR_NOT_NULL;
import static util.DataMessages.VALID_CUSTOMER;
import static util.DataMessages.VALID_MACHINE;
import static util.DataMessages.VALID_NULL_CUSTOMER;
import static util.DataMessages.VALID_NULL_MACHINE;
import static util.DataMessages.VALID_NULL_PRODUCT;
import static util.DataMessages.VALID_NULL_TYPE_PAYMENT;
import static util.DataMessages.VALID_PRODUCT;
import static util.DataMessages.VALID_TYPE_PAYMENT;
import static util.DataViews.ADD_AFTER_PURCHASES_PAGE;
import static util.DataViews.ATTR_CREDIT_CARD_ID;
import static util.DataViews.ATTR_CUSTOMER_ID;
import static util.DataViews.ATTR_DISCOUNT_ID;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_MACHINE_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_PRODUCT_ID;
import static util.DataViews.ATTR_PURCHASE;
import static util.DataViews.ATTR_PURCHASES_LIST;
import static util.DataViews.ATTR_TYPE_PAYMENT_ID;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_PURCHASE;
import static util.DataViews.PAGE_EDIT_PURCHASE;
import static util.DataViews.PAGE_LIST_PURCHASES;
import static util.DataViews.PAGE_REDIRECT_LIST_PURCHASES;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_PURCHASES_PAGE)
public class PurchaseController {
    private final IPurchaseService purchaseService;
    private final MessageSource messageSource;

    @GetMapping
    public String getPurchases(
            Model model,
            @RequestParam(required = false,
                    defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(required = false,
                    defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<PurchaseDto> pageable = purchaseService.findAll(pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_PURCHASES_PAGE);
        model.addAttribute(ATTR_PURCHASES_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());

        return PAGE_LIST_PURCHASES;
    }

    @GetMapping(URL_NEW)
    public String newPurchase(Model model) {
        model.addAllAttributes(purchaseService.getOrdersAttr(null));
        return PAGE_ADD_PURCHASE;
    }

    @PostMapping
    public String createPurchase(@ModelAttribute(ATTR_PURCHASE) @Valid PurchaseDto purchaseDto,
                                 BindingResult bindingResult,
                                 @RequestParam(value = ATTR_CUSTOMER_ID, required = false) Long customerId,
                                 @RequestParam(value = ATTR_MACHINE_ID, required = false) Long machineId,
                                 @RequestParam(value = ATTR_PRODUCT_ID, required = false) Long productId,
                                 @RequestParam(value = ATTR_TYPE_PAYMENT_ID, required = false) Integer typePaymentId,
                                 @RequestParam(value = ATTR_CREDIT_CARD_ID, required = false) Long creditCardId,
                                 @RequestParam(value = ATTR_DISCOUNT_ID, required = false) Integer discountId,
                                 Model model) {

        valid(bindingResult, customerId, machineId, productId, typePaymentId);

        if (bindingResult.hasErrors()) {
            model.addAllAttributes(purchaseService.getOrdersAttr(purchaseDto,
                    customerId,
                    machineId,
                    productId,
                    typePaymentId,
                    creditCardId,
                    discountId));
            return PAGE_ADD_PURCHASE;
        }

        purchaseService.save(purchaseDto,
                customerId, machineId, productId, typePaymentId, creditCardId,
                discountId, purchaseDto.getSumm());

        return PAGE_REDIRECT_LIST_PURCHASES;
    }

    @GetMapping(URL_EDIT)
    public String editPurchase(Model model,
                               @PathVariable(ATTR_ID) Long id) {
        model.addAllAttributes(purchaseService.getOrdersAttr(id));
        return PAGE_EDIT_PURCHASE;
    }

    @PatchMapping(URL_UPDATE)
    public String updatePurchase(@ModelAttribute(ATTR_PURCHASE) @Valid PurchaseDto purchaseDto,
                                 BindingResult bindingResult,
                                 @RequestParam(value = ATTR_CUSTOMER_ID, required = false) Long customerId,
                                 @RequestParam(value = ATTR_MACHINE_ID, required = false) Long machineId,
                                 @RequestParam(value = ATTR_PRODUCT_ID, required = false) Long productId,
                                 @RequestParam(value = ATTR_TYPE_PAYMENT_ID, required = false) Integer typePaymentId,
                                 @RequestParam(value = ATTR_CREDIT_CARD_ID, required = false) Long creditCardId,
                                 @RequestParam(value = ATTR_DISCOUNT_ID, required = false) Integer discountId,
                                 Model model) {

        valid(bindingResult, customerId, machineId, productId, typePaymentId);
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(purchaseService.getOrdersAttr(purchaseDto,
                    customerId,
                    machineId,
                    productId,
                    typePaymentId,
                    creditCardId,
                    discountId));
            return PAGE_EDIT_PURCHASE;
        }

        purchaseService.save(purchaseDto,
                customerId, machineId, productId, typePaymentId, creditCardId,
                discountId, purchaseDto.getSumm());

        return PAGE_REDIRECT_LIST_PURCHASES;
    }

    @DeleteMapping(URL_DELETE)
    public String deletePurchase(@PathVariable(ATTR_ID) Long id) {
        purchaseService.deleteById(id);
        return PAGE_REDIRECT_LIST_PURCHASES;
    }


    private void valid(BindingResult bindingResult, Long customer_id,
                       Long machine_id, Long product_id,
                       Integer type_payment_id) {
        if (customer_id == null) {
            String errorMessage = messageSource
                    .getMessage(MESSAGE_ERROR_NOT_NULL,
                            new Object[]{},
                            LocaleContextHolder.getLocale());

            bindingResult.addError(new FieldError(VALID_CUSTOMER, VALID_NULL_CUSTOMER,
                    errorMessage));
        }

        if (machine_id == null) {
            String errorMessage = messageSource
                    .getMessage(MESSAGE_ERROR_NOT_NULL,
                            new Object[]{},
                            LocaleContextHolder.getLocale());

            bindingResult.addError(new FieldError(VALID_MACHINE, VALID_NULL_MACHINE,
                    errorMessage));
        }

        if (product_id == null) {
            String errorMessage = messageSource
                    .getMessage(MESSAGE_ERROR_NOT_NULL,
                            new Object[]{},
                            LocaleContextHolder.getLocale());

            bindingResult.addError(new FieldError(VALID_PRODUCT, VALID_NULL_PRODUCT,
                    errorMessage));
        }

        if (type_payment_id == null) {
            String errorMessage = messageSource
                    .getMessage(MESSAGE_ERROR_NOT_NULL,
                            new Object[]{},
                            LocaleContextHolder.getLocale());

            bindingResult.addError(new FieldError(VALID_TYPE_PAYMENT, VALID_NULL_TYPE_PAYMENT,
                    errorMessage));
        }
    }

}
