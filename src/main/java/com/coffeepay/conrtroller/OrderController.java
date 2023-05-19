package com.coffeepay.conrtroller;

import com.coffeepay.dto.MachineDto;
import com.coffeepay.dto.PurchaseDto;
import com.coffeepay.service.IMachineService;
import com.coffeepay.service.IPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataViews.ADD_AFTER_ORDER_PAGE;
import static util.DataViews.ATTR_CREDIT_CARD_ID;
import static util.DataViews.ATTR_CUSTOMER_ID;
import static util.DataViews.ATTR_DISCOUNT_ID;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_MACHINE;
import static util.DataViews.ATTR_MACHINES_LIST;
import static util.DataViews.ATTR_MACHINE_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_PRODUCTS_LIST;
import static util.DataViews.ATTR_PRODUCT_ID;
import static util.DataViews.ATTR_PURCHASE;
import static util.DataViews.ATTR_PURCHASES_LIST;
import static util.DataViews.ATTR_SEARCH_MACHINE_CITY;
import static util.DataViews.ATTR_SEARCH_MACHINE_STREET;
import static util.DataViews.ATTR_SUM;
import static util.DataViews.ATTR_TYPE_PAYMENT_ID;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.NAME_LIST_ORDERS_HISTORY;
import static util.DataViews.NAME_LIST_ORDERS_MACHINES;
import static util.DataViews.PAGE_ORDERS_HISTORY;
import static util.DataViews.PAGE_ORDERS_INDEX;
import static util.DataViews.PAGE_ORDERS_MACHINES;
import static util.DataViews.PAGE_ORDERS_PAY;
import static util.DataViews.PAGE_ORDERS_PRODUCTS;
import static util.DataViews.URL_ORDER_HISTORY;
import static util.DataViews.URL_ORDER_MACHINES;
import static util.DataViews.URL_ORDER_PAY;
import static util.DataViews.URL_ORDER_PRODUCTS;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_ORDER_PAGE)
public class OrderController {
    private final IMachineService machineService;
    private final IPurchaseService purchaseService;
    private final MessageSource messageSource;

    @GetMapping
    public String getIndex() {
        return PAGE_ORDERS_INDEX;
    }

    @GetMapping(URL_ORDER_MACHINES)
    public String getMachines(Model model,
                              @RequestParam(value = ATTR_SEARCH_MACHINE_CITY,
                                      defaultValue = "") String city,
                              @RequestParam(value = ATTR_SEARCH_MACHINE_STREET,
                                      defaultValue = "") String street,
                              @RequestParam(required = false,
                                      defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(required = false,
                                      defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<MachineDto> pageable = machineService.findAllByCityAndStreet(city, street, pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, NAME_LIST_ORDERS_MACHINES);
        model.addAttribute(ATTR_MACHINES_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_MACHINE_CITY, city);
        model.addAttribute(ATTR_SEARCH_MACHINE_STREET, street);

        return PAGE_ORDERS_MACHINES;
    }

    @GetMapping(URL_ORDER_PRODUCTS)
    public String getProducts(Model model,
                              @PathVariable(ATTR_ID) Long id) {

        MachineDto machineDto = machineService.findById(id);
        model.addAttribute(ATTR_MACHINE, machineDto);
        model.addAttribute(ATTR_PRODUCTS_LIST, machineDto.getProducts());

        return PAGE_ORDERS_PRODUCTS;
    }

    @GetMapping(URL_ORDER_PAY)
    public String getProducts(Model model,
                              @ModelAttribute(ATTR_PURCHASE) PurchaseDto purchaseDto,
                              @PathVariable(ATTR_MACHINE_ID) Long machineId,
                              @PathVariable(ATTR_ID) Long productId) {

        model.addAllAttributes(purchaseService.getOrdersAttr(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                machineId,
                productId));

        return PAGE_ORDERS_PAY;
    }

    @PostMapping
    public String createOrder(@ModelAttribute(ATTR_PURCHASE) @Valid PurchaseDto purchaseDto,
                              @RequestParam(value = ATTR_CUSTOMER_ID, required = false) Long customerId,
                              @RequestParam(value = ATTR_MACHINE_ID, required = false) Long machineId,
                              @RequestParam(value = ATTR_PRODUCT_ID, required = false) Long productId,
                              @RequestParam(value = ATTR_TYPE_PAYMENT_ID, required = false) Integer typePaymentId,
                              @RequestParam(value = ATTR_CREDIT_CARD_ID, required = false) Long creditCardId,
                              @RequestParam(value = ATTR_DISCOUNT_ID, required = false) Integer discountId,
                              @RequestParam(value = ATTR_SUM, required = false) Float summ) {

        purchaseService.save(purchaseDto, customerId, machineId, productId,
                typePaymentId, creditCardId, discountId, summ);

        return PAGE_ORDERS_INDEX;
    }

    @GetMapping(URL_ORDER_HISTORY)
    public String getPurchases(
            Model model,
            @RequestParam(required = false,
                    defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(required = false,
                    defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<PurchaseDto> pageable = purchaseService.findAllByCustomer(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, NAME_LIST_ORDERS_HISTORY);
        model.addAttribute(ATTR_PURCHASES_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());

        return PAGE_ORDERS_HISTORY;
    }
}
