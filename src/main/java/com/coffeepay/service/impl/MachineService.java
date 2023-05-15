package com.coffeepay.service.impl;

import com.coffeepay.dto.MachineDto;
import com.coffeepay.model.Machine;
import com.coffeepay.repository.MachineRepository;
import com.coffeepay.service.IMachineService;
import com.coffeepay.specification.MachineSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static util.DataGeneral.MACHINE_CLASS;
import static util.DataGeneral.MACHINE_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class MachineService implements IMachineService {
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<MachineDto> findAll(String serialNumber,
                                    String nameModel,
                                    String brand,
                                    String city,
                                    String street,
                                    Pageable pageable) {
        Specification<Machine> allFields = Specification.
                where(MachineSpecification.likeSerialNumber(serialNumber))
                .and(MachineSpecification.likeModel(nameModel))
                .and(MachineSpecification.likeBrand(brand))
                .and(MachineSpecification.likeCity(city))
                .and(MachineSpecification.likeStreet(street));

        Page<Machine> machinePage = machineRepository.findAll(allFields, pageable);

        return new PageImpl<>(
                machinePage
                        .stream()
                        .map(machine -> modelMapper.map(machine, MACHINE_DTO_CLASS))
                        .toList(),
                pageable,
                machinePage.getTotalElements());
    }

    @Override
    public void save(MachineDto machineDto) {
        machineRepository.save(modelMapper.map(machineDto, MACHINE_CLASS));
    }

    @Override
    public void update(MachineDto machineDto) {
        machineRepository.save(modelMapper.map(machineDto, MACHINE_CLASS));
    }

    @Override
    public MachineDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(machineRepository::findById)
                .map(machine -> modelMapper.map(machine, MACHINE_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        machineRepository.deleteById(id);
    }
}
