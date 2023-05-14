package com.coffeepay.service;

import com.coffeepay.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<ProductDto> findAll(String name, Float minPrice, Float maxPrice, Pageable pageable);

    void save(ProductDto productDto);

    void update(ProductDto productDto);

    ProductDto findById(Long id);

    void deleteById(Long id);
}
