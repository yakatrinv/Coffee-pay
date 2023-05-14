package com.coffeepay.service.impl;

import com.coffeepay.dto.AddressDto;
import com.coffeepay.model.Address;
import com.coffeepay.repository.AddressRepository;
import com.coffeepay.service.IAddressService;
import com.coffeepay.specification.AddressSpecification;
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

import static util.DataGeneral.ADDRESS_CLASS;
import static util.DataGeneral.ADDRESS_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<AddressDto> findAllPage(String city, String street, Pageable pageable) {
        Specification<Address> likeCityAndStreet = Specification.where(AddressSpecification.likeCity(city)
                .and(AddressSpecification.likeStreet(street)));

        Page<Address> addressesPage = addressRepository.findAll(likeCityAndStreet, pageable);

        return new PageImpl<>(
                addressesPage
                        .stream()
                        .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                        .toList(),
                pageable,
                addressesPage.getTotalElements());
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                .toList();
    }

    @Override
    public void save(AddressDto addressDto) {
        addressRepository.save(modelMapper.map(addressDto, ADDRESS_CLASS));
    }

    @Override
    public void update(AddressDto addressDto) {
        addressRepository.save(modelMapper.map(addressDto, ADDRESS_CLASS));
    }

    @Override
    public AddressDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(addressRepository::findById)
                .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
