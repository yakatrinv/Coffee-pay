package com.coffeepay.service.impl;

import com.coffeepay.dto.DiscountDto;
import com.coffeepay.model.Discount;
import com.coffeepay.repository.DiscountRepository;
import com.coffeepay.service.IDiscountService;
import com.coffeepay.specification.DiscountSpecification;
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

import static util.DataGeneral.DISCOUNT_CLASS;
import static util.DataGeneral.DISCOUNT_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscountService implements IDiscountService {
    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<DiscountDto> findAll(int minSum,
                                     int maxSum,
                                     int minPercent,
                                     int maxPercent,
                                     Pageable pageable) {
        Specification<Discount> allFields = Specification
                .where(DiscountSpecification
                        .betweenPercent(minPercent, maxPercent))
                .and(DiscountSpecification.betweenSum(minSum, maxSum));

        Page<Discount> discountPage = discountRepository.findAll(allFields, pageable);

        return new PageImpl<>(
                discountPage
                        .stream()
                        .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                        .toList(),
                pageable,
                discountPage.getTotalElements());
    }

    @Override
    public List<DiscountDto> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                .toList();
    }

    @Override
    public void save(DiscountDto discountDto) {
        discountRepository.save(modelMapper.map(discountDto, DISCOUNT_CLASS));
    }

    @Override
    public void update(DiscountDto discountDto) {
        discountRepository.save(modelMapper.map(discountDto, DISCOUNT_CLASS));
    }

    @Override
    public DiscountDto findById(Integer id) {
        return Optional.ofNullable(id)
                .map(discountRepository::findById)
                .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        discountRepository.deleteById(id);
    }

    @Override
    public DiscountDto findBySumAndByCustomer(Long id) {
        return Optional.ofNullable(id)
                .map(discountRepository::findBySumAndByCustomer)
                .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                .orElse(null);
    }
}
