package com.coffeepay.service;

import com.coffeepay.dto.PurchaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface IPurchaseService {
    Page<PurchaseDto> findAll(Pageable pageable);

    Page<PurchaseDto> findAllByCustomer(String username,
                                        Pageable pageable);

    void save(PurchaseDto purchaseDto);

    void update(PurchaseDto purchaseDto);

    PurchaseDto findById(Long id);

    void deleteById(Long id);

    Map<String, Object> getOrdersAttr(String username,
                                      Long machineId,
                                      Long productId);

    Map<String, Object> getOrdersAttr(Long id);

    Map<String, Object> getOrdersAttr(PurchaseDto purchaseDto,
                                      Long customerId,
                                      Long machineId,
                                      Long productId,
                                      Integer typePaymentId,
                                      Long creditCardId,
                                      Integer discountId);

    void save(PurchaseDto purchaseDto, Long customerId,
              Long machineId, Long productId,
              Integer typePaymentId, Long creditCardId,
              Integer discountId, Float summ);
}
