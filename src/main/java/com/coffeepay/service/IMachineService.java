package com.coffeepay.service;

import com.coffeepay.dto.MachineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface IMachineService {
    Page<MachineDto> findAll(String serialNumber,
                             String nameModel,
                             String brand,
                             String city,
                             String street,
                             Pageable pageable);

    void save(MachineDto machineDto);

    void update(MachineDto machineDto);

    MachineDto findById(Long id);

    void deleteById(Long id);
}
