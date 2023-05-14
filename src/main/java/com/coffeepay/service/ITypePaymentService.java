package com.coffeepay.service;

import com.coffeepay.dto.TypePaymentDto;

import java.util.List;

public interface ITypePaymentService {
    void save(TypePaymentDto typePaymentDto);

    TypePaymentDto findById(Integer id);

    void deleteById(Integer id);

    List<TypePaymentDto> getAll();
}
