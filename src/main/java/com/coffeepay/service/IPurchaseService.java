package com.coffeepay.service;

import com.coffeepay.dto.PurchaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPurchaseService {
    Page<PurchaseDto> findAll(Pageable pageable);

    Page<PurchaseDto> findAllByCustomer(String username,
                                        Pageable pageable);

    void save(PurchaseDto purchaseDto);

    void update(PurchaseDto purchaseDto);

    PurchaseDto findById(Long id);

    void deleteById(Long id);
}
