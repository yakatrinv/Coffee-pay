package com.coffeepay.service;

import com.coffeepay.dto.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAddressService {
    Page<AddressDto> findAllPage(String city, String street, Pageable pageable);

    List<AddressDto> getAllAddresses();

    void save(AddressDto addressDto);

    void update(AddressDto addressDto);

    AddressDto findById(Long id);

    void deleteById(Long id);
}
