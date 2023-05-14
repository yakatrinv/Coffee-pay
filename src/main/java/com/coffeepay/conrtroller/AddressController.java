package com.coffeepay.conrtroller;

import com.coffeepay.dto.AddressDto;
import com.coffeepay.service.IAddressService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataViews.ADD_AFTER_ADDRESS_PAGE;
import static util.DataViews.ATTR_ADDRESS;
import static util.DataViews.ATTR_ADDRESSES_LIST;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_SEARCH_ADDRESS_CITY;
import static util.DataViews.ATTR_SEARCH_ADDRESS_STREET;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_ADDRESS;
import static util.DataViews.PAGE_EDIT_ADDRESS;
import static util.DataViews.PAGE_LIST_ADDRESSES;
import static util.DataViews.PAGE_PREV_URL;
import static util.DataViews.PAGE_REDIRECT_LIST_ADDRESSES;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_MAIN;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_ADDRESS_PAGE)
public class AddressController {
    private final IAddressService addressService;

    @GetMapping
    public String getAddresses(Model model,
                               @RequestParam(value = ATTR_SEARCH_ADDRESS_CITY, defaultValue = "") String city,
                               @RequestParam(value = ATTR_SEARCH_ADDRESS_STREET, defaultValue = "") String street,
                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<AddressDto> pageable = addressService.findAll(city, street, pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_ADDRESS_PAGE);
        model.addAttribute(ATTR_ADDRESSES_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE, pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_ADDRESS_CITY, city);
        model.addAttribute(ATTR_SEARCH_ADDRESS_STREET, street);

        return PAGE_LIST_ADDRESSES;
    }

    @GetMapping(URL_NEW)
    public String newAddress(Model model) {
        model.addAttribute(ATTR_ADDRESS, new AddressDto());

        return PAGE_ADD_ADDRESS;
    }

    @PostMapping
    public String createAddress(@ModelAttribute(ATTR_ADDRESS) @Valid AddressDto addressDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_ADD_ADDRESS;
        }
        addressService.save(addressDto);

        return PAGE_REDIRECT_LIST_ADDRESSES;
    }

    @GetMapping(URL_EDIT)
    public String editAddress(Model model,
                              @PathVariable(ATTR_ID) long id) {

        model.addAttribute(ATTR_ADDRESS, addressService.findById(id));

        return PAGE_EDIT_ADDRESS;
    }

    @PatchMapping(URL_UPDATE)
    public String updateAddress(@ModelAttribute(ATTR_ADDRESS) @Valid AddressDto addressDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_ADDRESS;
        }

        addressService.save(addressDto);

        return PAGE_REDIRECT_LIST_ADDRESSES;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteAddress(@PathVariable(ATTR_ID) long id) {

        addressService.deleteById(id);

        return PAGE_REDIRECT_LIST_ADDRESSES;
    }

}
