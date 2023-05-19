package com.coffeepay.service;

import com.coffeepay.dto.MachineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IMachineService {
    Page<MachineDto> findAll(String serialNumber,
                             String nameModel,
                             String brand,
                             String city,
                             String street,
                             Pageable pageable);

    Page<MachineDto> findAllByCityAndStreet(String city,
                                            String street,
                                            Pageable pageable);

    List<MachineDto> getAllMachines();

    void save(MachineDto machineDto);

    void save(MachineDto machineDto, Long addressId, Long modelId);

    void update(MachineDto machineDto);

    MachineDto findById(Long id);

    void deleteById(Long id);

    Map<String, Object> getMachineAttribute(MachineDto machineDto, Long addressId, Long modelId);
    Map<String, Object> getMachineAttribute(Long id);
    Map<String, Object> getMachineAttribute();
}
