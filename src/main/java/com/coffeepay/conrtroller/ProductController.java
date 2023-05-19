package com.coffeepay.conrtroller;

import com.coffeepay.dto.ProductDto;
import com.coffeepay.service.IProductService;
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

import static util.DataViews.ADD_AFTER_PRODUCTS_PAGE;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_PRODUCT;
import static util.DataViews.ATTR_PRODUCTS_LIST;
import static util.DataViews.ATTR_SEARCH_PRODUCT_MAX_PRICE;
import static util.DataViews.ATTR_SEARCH_PRODUCT_MIN_PRICE;
import static util.DataViews.ATTR_SEARCH_PRODUCT_NAME;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_PRODUCT;
import static util.DataViews.PAGE_EDIT_PRODUCT;
import static util.DataViews.PAGE_LIST_PRODUCTS;
import static util.DataViews.PAGE_REDIRECT_LIST_PRODUCTS;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_PRODUCTS_PAGE)
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public String getProduct(Model model,
                             @RequestParam(value = ATTR_SEARCH_PRODUCT_NAME, defaultValue = "") String name,
                             @RequestParam(value = ATTR_SEARCH_PRODUCT_MIN_PRICE, defaultValue = "0") Float minPrice,
                             @RequestParam(value = ATTR_SEARCH_PRODUCT_MAX_PRICE, defaultValue = "0") Float maxPrice,
                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductDto> pageable = productService.findAll(name, minPrice, maxPrice, pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_PRODUCTS_PAGE);
        model.addAttribute(ATTR_PRODUCTS_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_PRODUCT_NAME, name);
        model.addAttribute(ATTR_SEARCH_PRODUCT_MIN_PRICE, minPrice);
        model.addAttribute(ATTR_SEARCH_PRODUCT_MAX_PRICE, maxPrice);

        return PAGE_LIST_PRODUCTS;
    }

    @GetMapping(URL_NEW)
    public String newProduct(Model model) {
        model.addAttribute(ATTR_PRODUCT, new ProductDto());
        return PAGE_ADD_PRODUCT;
    }

    @PostMapping
    public String createProduct(@ModelAttribute(ATTR_PRODUCT) @Valid ProductDto productDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return PAGE_ADD_PRODUCT;
        }
        productService.save(productDto);
        return PAGE_REDIRECT_LIST_PRODUCTS;
    }

    @GetMapping(URL_EDIT)
    public String editProduct(Model model,
                              @PathVariable(ATTR_ID) long id) {
        model.addAttribute(ATTR_PRODUCT, productService.findById(id));
        return PAGE_EDIT_PRODUCT;
    }

    @PatchMapping(URL_UPDATE)
    public String updateProduct(@ModelAttribute(ATTR_PRODUCT) @Valid ProductDto productDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_PRODUCT;
        }
        productService.save(productDto);
        return PAGE_REDIRECT_LIST_PRODUCTS;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteProduct(@PathVariable(ATTR_ID) long id) {
        productService.deleteById(id);
        return PAGE_REDIRECT_LIST_PRODUCTS;
    }
}
