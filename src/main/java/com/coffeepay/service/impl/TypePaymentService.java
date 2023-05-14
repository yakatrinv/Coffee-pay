package com.coffeepay.service.impl;

import com.coffeepay.dto.TypePaymentDto;
import com.coffeepay.repository.TypePaymentRepository;
import com.coffeepay.service.ITypePaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static util.DataGeneral.TYPE_PAYMENT_CLASS;
import static util.DataGeneral.TYPE_PAYMENT_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class TypePaymentService implements ITypePaymentService {
    private final TypePaymentRepository typePaymentRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(TypePaymentDto typePaymentDto) {
        typePaymentRepository.save(modelMapper.map(typePaymentDto, TYPE_PAYMENT_CLASS));
    }

    @Override
    public TypePaymentDto findById(Integer id) {
        return modelMapper.map(typePaymentRepository.findById(id), TYPE_PAYMENT_DTO_CLASS);
    }

    @Override
    public List<TypePaymentDto> getAll() {
        return typePaymentRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment,
                        TYPE_PAYMENT_DTO_CLASS))
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        typePaymentRepository.deleteById(id);
    }
}
