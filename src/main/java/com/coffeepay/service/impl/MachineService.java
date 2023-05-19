package com.coffeepay.service.impl;

import com.coffeepay.dto.AddressDto;
import com.coffeepay.dto.MachineDto;
import com.coffeepay.dto.ModelMachineDto;
import com.coffeepay.model.Machine;
import com.coffeepay.repository.AddressRepository;
import com.coffeepay.repository.MachineRepository;
import com.coffeepay.repository.ModelMachineRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static util.DataGeneral.ADDRESS_DTO_CLASS;
import static util.DataGeneral.MACHINE_CLASS;
import static util.DataGeneral.MACHINE_DTO_CLASS;
import static util.DataGeneral.MODEL_MACHINE_DTO_CLASS;
import static util.DataViews.ATTR_ADDRESS;
import static util.DataViews.ATTR_ADDRESSES_LIST;
import static util.DataViews.ATTR_MACHINE;
import static util.DataViews.ATTR_MODELS_MACHINE_LIST;
import static util.DataViews.ATTR_MODEL_MACHINE;

@Service
@Transactional
@RequiredArgsConstructor
public class MachineService implements IMachineService {
    private final MachineRepository machineRepository;
    private final ModelMachineRepository modelMachineRepository;
    private final AddressRepository addressRepository;
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
    public Page<MachineDto> findAllByCityAndStreet(String city,
                                                   String street,
                                                   Pageable pageable) {
        Specification<Machine> allFields = Specification.
                where(MachineSpecification.likeCity(city))
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
    public List<MachineDto> getAllMachines() {
        return machineRepository.findAll()
                .stream()
                .map(machine -> modelMapper.map(machine, MACHINE_DTO_CLASS))
                .toList();
    }

    @Override
    public void save(MachineDto machineDto) {
        machineRepository.save(modelMapper.map(machineDto, MACHINE_CLASS));
    }

    @Override
    public void save(MachineDto machineDto, Long addressId, Long modelId) {
        Machine machine = modelMapper.map(machineDto, MACHINE_CLASS);
        machine.setAddress(
                Optional.ofNullable(addressId)
                        .map(addressRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .orElse(null)
        );
        machine.setModel(
                Optional.ofNullable(modelId)
                        .map(modelMachineRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .orElse(null)
        );
        machineRepository.save(machine);
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

    @Override
    public Map<String, Object> getMachineAttribute() {
        Map<String, Object> result = new HashMap<>();

        result.put(ATTR_ADDRESSES_LIST, addressRepository.findAll()
                .stream()
                .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                .toList());

        result.put(ATTR_MODELS_MACHINE_LIST,
                modelMachineRepository.findAll()
                        .stream()
                        .map(model -> modelMapper.map(model, MODEL_MACHINE_DTO_CLASS))
                        .toList()
        );

        result.put(ATTR_MACHINE, new MachineDto());

        return result;
    }

    @Override
    public Map<String, Object> getMachineAttribute(MachineDto machineDto, Long addressId, Long modelId) {
        Map<String, Object> result = new HashMap<>();

        result.put(ATTR_ADDRESSES_LIST, addressRepository.findAll()
                .stream()
                .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                .toList());

        result.put(ATTR_MODELS_MACHINE_LIST,
                modelMachineRepository.findAll()
                        .stream()
                        .map(model -> modelMapper.map(model, MODEL_MACHINE_DTO_CLASS))
                        .toList()
        );

        ModelMachineDto modelMachineDto = Optional.ofNullable(modelId)
                .map(modelMachineRepository::findById)
                .map(model -> modelMapper.map(model, MODEL_MACHINE_DTO_CLASS))
                .orElse(null);
        AddressDto addressDto = Optional.ofNullable(addressId)
                .map(addressRepository::findById)
                .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                .orElse(null);
        machineDto.setModel(modelMachineDto);
        machineDto.setAddress(addressDto);

        result.put(ATTR_MACHINE, machineDto);

        return result;
    }

    @Override
    public Map<String, Object> getMachineAttribute(Long id) {
        Map<String, Object> result = new HashMap<>();

        result.put(ATTR_ADDRESSES_LIST, addressRepository.findAll()
                .stream()
                .map(address -> modelMapper.map(address, ADDRESS_DTO_CLASS))
                .toList());

        result.put(ATTR_MODELS_MACHINE_LIST,
                modelMachineRepository.findAll()
                        .stream()
                        .map(model -> modelMapper.map(model, MODEL_MACHINE_DTO_CLASS))
                        .toList()
        );

        MachineDto machineDto = Optional.ofNullable(id)
                .map(machineRepository::findById)
                .map(machine -> modelMapper.map(machine, MACHINE_DTO_CLASS))
                .orElse(new MachineDto());
        result.put(ATTR_MACHINE,
                machineDto);

        return result;
    }
}
