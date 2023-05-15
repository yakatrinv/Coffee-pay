package com.coffeepay.service;

import com.coffeepay.dto.DiscountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDiscountService {
    Page<DiscountDto> findAll(int minSum, int maxSum, int minPercent, int maxPercent, Pageable pageable);

    void save(DiscountDto discountDto);

    void update(DiscountDto discountDto);

    DiscountDto findById(Integer id);

    void deleteById(Integer id);
}
