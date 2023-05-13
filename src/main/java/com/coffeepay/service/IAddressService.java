package com.coffeepay.service;

import com.coffeepay.dto.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAddressService {
    Page<AddressDto> findAll(String city, String street,Pageable pageable);

    void save(AddressDto addressDto);

    void update(AddressDto addressDto);

    AddressDto findById(Long id);

    void deleteById(Long id);
}
