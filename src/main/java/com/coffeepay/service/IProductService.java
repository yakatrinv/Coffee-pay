package com.coffeepay.service;

import com.coffeepay.dto.DiscountDto;
import com.coffeepay.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<ProductDto> findAll(String name, Float minPrice, Float maxPrice, Pageable pageable);

    List<ProductDto> getAllProducts();

    void save(ProductDto productDto);

    void update(ProductDto productDto);

    ProductDto findById(Long id);

    void deleteById(Long id);

    Float getSumOrder(DiscountDto discountDto, ProductDto productDto);
}
