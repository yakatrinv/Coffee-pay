package com.coffeepay.service;

import com.coffeepay.dto.PurchaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IPurchaseService {
    Page<PurchaseDto> findAll(LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

    void save(PurchaseDto purchaseDto);

    void update(PurchaseDto purchaseDto);

    PurchaseDto findById(Long id);

    void deleteById(Long id);
}
