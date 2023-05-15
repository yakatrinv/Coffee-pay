package com.coffeepay.service.impl;

import com.coffeepay.dto.PurchaseDto;
import com.coffeepay.model.Purchase;
import com.coffeepay.repository.PurchaseRepository;
import com.coffeepay.service.IPurchaseService;
import com.coffeepay.specification.PurchaseSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static util.DataGeneral.PURCHASE_CLASS;
import static util.DataGeneral.PURCHASE_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService implements IPurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<PurchaseDto> findAll(LocalDate dateFrom,
                                     LocalDate dateTo,
                                     Pageable pageable) {
        Specification<Purchase> allFields = Specification.
                where(PurchaseSpecification.betweenDate(dateFrom, dateTo));

        Page<Purchase> machinePage = purchaseRepository.findAll(allFields, pageable);

        return new PageImpl<>(
                machinePage
                        .stream()
                        .map(purchase -> modelMapper.map(purchase, PURCHASE_DTO_CLASS))
                        .toList(),
                pageable,
                machinePage.getTotalElements());
    }

    @Override
    public void save(PurchaseDto purchaseDto) {
        purchaseRepository.save(modelMapper.map(purchaseDto, PURCHASE_CLASS));
    }

    @Override
    public void update(PurchaseDto purchaseDto) {
        purchaseRepository.save(modelMapper.map(purchaseDto, PURCHASE_CLASS));
    }

    @Override
    public PurchaseDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(purchaseRepository::findById)
                .map(purchase -> modelMapper.map(purchase, PURCHASE_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }
}
