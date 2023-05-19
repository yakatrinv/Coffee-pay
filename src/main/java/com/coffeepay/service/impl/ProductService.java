package com.coffeepay.service.impl;

import com.coffeepay.dto.DiscountDto;
import com.coffeepay.dto.ProductDto;
import com.coffeepay.model.Product;
import com.coffeepay.repository.ProductRepository;
import com.coffeepay.service.IProductService;
import com.coffeepay.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static util.DataGeneral.PRODUCT_CLASS;
import static util.DataGeneral.PRODUCT_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductDto> findAll(String name, Float minPrice, Float maxPrice, Pageable pageable) {
        Specification<Product> likeNameAndBetweenPrice = Specification
                .where(ProductSpecification.likeName(name))
                .and(ProductSpecification.betweenPrice(minPrice, maxPrice));

        Page<Product> productPage = productRepository.findAll(likeNameAndBetweenPrice, pageable);

        return new PageImpl<>(
                productPage
                        .stream()
                        .map(address -> modelMapper.map(address, PRODUCT_DTO_CLASS))
                        .toList(),
                pageable,
                productPage.getTotalElements());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, PRODUCT_DTO_CLASS))
                .toList();
    }

    @Override
    public void save(ProductDto productDto) {
        productRepository.save(modelMapper.map(productDto, PRODUCT_CLASS));
    }

    @Override
    public void update(ProductDto productDto) {
        productRepository.save(modelMapper.map(productDto, PRODUCT_CLASS));
    }

    @Override
    public ProductDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(productRepository::findById)
                .map(product -> modelMapper.map(product, PRODUCT_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
