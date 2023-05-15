package com.coffeepay.conrtroller;

import com.coffeepay.dto.MachineDto;
import com.coffeepay.service.IMachineService;
import com.coffeepay.service.IPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static util.DataViews.ADD_AFTER_MACHINE_PAGE;
import static util.DataViews.ADD_AFTER_ORDER_PAGE;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_MACHINE;
import static util.DataViews.ATTR_MACHINES_LIST;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_PRODUCTS_LIST;
import static util.DataViews.ATTR_SEARCH_MACHINE_BRAND;
import static util.DataViews.ATTR_SEARCH_MACHINE_CITY;
import static util.DataViews.ATTR_SEARCH_MACHINE_MODEL;
import static util.DataViews.ATTR_SEARCH_MACHINE_SERIAL_NUMBER;
import static util.DataViews.ATTR_SEARCH_MACHINE_STREET;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.NAME_LIST_ORDERS_MACHINES;
import static util.DataViews.PAGE_ORDERS_INDEX;
import static util.DataViews.PAGE_ORDERS_MACHINES;
import static util.DataViews.PAGE_ORDERS_PRODUCTS;
import static util.DataViews.URL_ORDER_MACHINES;
import static util.DataViews.URL_ORDER_PRODUCTS;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_ORDER_PAGE)
public class OrderController {
    private final IMachineService machineService;
    private final IPurchaseService purchaseService;

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
        model.addAttribute(ATTR_PRODUCTS_LIST,machineDto.getProducts());

        return PAGE_ORDERS_PRODUCTS;
    }
//    /orders/machines/{id}/products(id=${machine.id})}

//    @GetMapping
//    public String getAddresses(Model model,
//                               @RequestParam(value = ATTR_SEARCH_ADDRESS_CITY, defaultValue = "") String city,
//                               @RequestParam(value = ATTR_SEARCH_ADDRESS_STREET, defaultValue = "") String street,
//                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
//                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
//
//        PageRequest pageRequest = PageRequest.of(page - 1, size);
//        Page<AddressDto> pageable = purchaseService.findAllPage(city, street, pageRequest);
//
//        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_ADDRESS_PAGE);
//        model.addAttribute(ATTR_ADDRESSES_LIST, pageable.getContent());
//        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
//        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
//        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
//                pageable.getTotalPages() == 0 ?
//                        pageable.getTotalPages() + 1 :
//                        pageable.getTotalPages());
//        model.addAttribute(ATTR_SEARCH_ADDRESS_CITY, city);
//        model.addAttribute(ATTR_SEARCH_ADDRESS_STREET, street);
//
//        return PAGE_LIST_ADDRESSES;
//    }
//
//    @GetMapping(URL_NEW)
//    public String newAddress(Model model) {
//        model.addAttribute(ATTR_ADDRESS, new AddressDto());
//
//        return PAGE_ADD_ADDRESS;
//    }
//
//    @PostMapping
//    public String createAddress(@ModelAttribute(ATTR_ADDRESS) @Valid AddressDto addressDto,
//                                BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return PAGE_ADD_ADDRESS;
//        }
//        purchaseService.save(addressDto);
//
//        return PAGE_REDIRECT_LIST_ADDRESSES;
//    }
//
//    @GetMapping(URL_EDIT)
//    public String editAddress(Model model,
//                              @PathVariable(ATTR_ID) long id) {
//
//        model.addAttribute(ATTR_ADDRESS, purchaseService.findById(id));
//
//        return PAGE_EDIT_ADDRESS;
//    }
//
//    @PatchMapping(URL_UPDATE)
//    public String updateAddress(@ModelAttribute(ATTR_ADDRESS) @Valid AddressDto addressDto,
//                                BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return PAGE_EDIT_ADDRESS;
//        }
//
//        purchaseService.save(addressDto);
//
//        return PAGE_REDIRECT_LIST_ADDRESSES;
//    }
//
//    @DeleteMapping(URL_DELETE)
//    public String deleteAddress(@PathVariable(ATTR_ID) long id) {
//
//        purchaseService.deleteById(id);
//
//        return PAGE_REDIRECT_LIST_ADDRESSES;
//    }

}
