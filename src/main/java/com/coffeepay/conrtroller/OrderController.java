package com.coffeepay.conrtroller;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.DiscountDto;
import com.coffeepay.dto.MachineDto;
import com.coffeepay.dto.ProductDto;
import com.coffeepay.dto.PurchaseDto;
import com.coffeepay.service.ICreditCardService;
import com.coffeepay.service.ICustomerService;
import com.coffeepay.service.IDiscountService;
import com.coffeepay.service.IMachineService;
import com.coffeepay.service.IProductService;
import com.coffeepay.service.IPurchaseService;
import com.coffeepay.service.ITypePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import static util.DataViews.ADD_AFTER_ORDER_PAGE;
import static util.DataViews.ATTR_CREDIT_CARDS;
import static util.DataViews.ATTR_CREDIT_CARD_ID;
import static util.DataViews.ATTR_CUSTOMER;
import static util.DataViews.ATTR_CUSTOMERS;
import static util.DataViews.ATTR_CUSTOMER_ID;
import static util.DataViews.ATTR_DISCOUNT;
import static util.DataViews.ATTR_DISCOUNTS_LIST;
import static util.DataViews.ATTR_DISCOUNT_ID;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_MACHINE;
import static util.DataViews.ATTR_MACHINES_LIST;
import static util.DataViews.ATTR_MACHINE_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_PRODUCT;
import static util.DataViews.ATTR_PRODUCTS_LIST;
import static util.DataViews.ATTR_PRODUCT_ID;
import static util.DataViews.ATTR_PURCHASE;
import static util.DataViews.ATTR_PURCHASES_LIST;
import static util.DataViews.ATTR_SEARCH_MACHINE_CITY;
import static util.DataViews.ATTR_SEARCH_MACHINE_STREET;
import static util.DataViews.ATTR_TYPE_PAYMENTS_LIST;
import static util.DataViews.ATTR_TYPE_PAYMENT_ID;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.NAME_LIST_ORDERS_HISTORY;
import static util.DataViews.NAME_LIST_ORDERS_MACHINES;
import static util.DataViews.PAGE_EDIT_PURCHASE;
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
    private final IProductService productService;
    private final ITypePaymentService typePaymentService;
    private final ICustomerService customerService;
    private final IDiscountService discountService;
    private final ICreditCardService creditCardService;
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
                              @ModelAttribute(ATTR_PURCHASE) @Valid PurchaseDto purchaseDto,
                              @PathVariable(ATTR_MACHINE_ID) Long machine_id,
                              @PathVariable(ATTR_ID) Long id) {


        CustomerDto customerDto = customerService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        model.addAttribute(ATTR_MACHINE, machineService.findById(machine_id));
        ProductDto productDto = productService.findById(id);
        model.addAttribute(ATTR_PRODUCT, productDto);
        model.addAttribute(ATTR_CUSTOMER, customerDto);
        model.addAttribute(ATTR_TYPE_PAYMENTS_LIST, typePaymentService.getAll());
        model.addAttribute(ATTR_CREDIT_CARDS, customerDto.getCreditCards());

        DiscountDto discountDto = discountService.findBySumAndByCustomer(customerDto.getId());
        model.addAttribute(ATTR_DISCOUNT, discountDto);

        PurchaseDto newOrder = new PurchaseDto();
        newOrder.setPrice(productDto.getPrice());
        newOrder.setSumm(productService.getSumOrder(discountDto, productDto));
        model.addAttribute(ATTR_PURCHASE, newOrder);

        return PAGE_ORDERS_PAY;
    }

    @PostMapping
    public String createOrder(@ModelAttribute(ATTR_PURCHASE) @Valid PurchaseDto purchaseDto,
                              BindingResult bindingResult,
                              @RequestParam(value = ATTR_CUSTOMER_ID, required = false) Long customer_id,
                              @RequestParam(value = ATTR_MACHINE_ID, required = false) Long machine_id,
                              @RequestParam(value = ATTR_PRODUCT_ID, required = false) Long product_id,
                              @RequestParam(value = ATTR_TYPE_PAYMENT_ID, required = false) Integer type_payment_id,
                              @RequestParam(value = ATTR_CREDIT_CARD_ID, required = false) Long credit_card_id,
                              @RequestParam(value = ATTR_DISCOUNT_ID, required = false) Integer discount_id,
                              Model model) {

        valid(bindingResult, customer_id, machine_id, product_id, type_payment_id);

        if (bindingResult.hasErrors()) {
            fillListAttr(model);
            return PAGE_EDIT_PURCHASE;
        }

        ProductDto productDto = productService.findById(product_id);
        purchaseDto.setCustomer(customerService.findById(customer_id));
        purchaseDto.setMachine(machineService.findById(machine_id));
        purchaseDto.setTypePayment(typePaymentService.findById(type_payment_id));
        purchaseDto.setCreditCard(creditCardService.findById(credit_card_id));
        purchaseDto.setDiscount(discountService.findById(discount_id));
        purchaseDto.setProduct(productDto);
        purchaseDto.setPrice(productDto.getPrice());

        purchaseService.save(purchaseDto);
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

    private void valid(BindingResult bindingResult, Long customer_id, Long machine_id, Long product_id, Integer type_payment_id) {
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

    private void fillListAttr(Model model) {
        model.addAttribute(ATTR_CUSTOMERS, customerService.getAllCustomers());
        model.addAttribute(ATTR_MACHINES_LIST, machineService.getAllMachines());
        model.addAttribute(ATTR_PRODUCTS_LIST, productService.getAllProducts());
        model.addAttribute(ATTR_DISCOUNTS_LIST, discountService.getAllDiscounts());
        model.addAttribute(ATTR_TYPE_PAYMENTS_LIST, typePaymentService.getAll());
        model.addAttribute(ATTR_CREDIT_CARDS, creditCardService.getAllCreditCards());
    }
}
