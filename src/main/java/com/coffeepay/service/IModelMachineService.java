package com.coffeepay.service;

import com.coffeepay.dto.ModelMachineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IModelMachineService {
    Page<ModelMachineDto> findAll(String model, String brand, Pageable pageable);

    void save(ModelMachineDto modelMachineDto);

    void update(ModelMachineDto modelMachineDto);

    ModelMachineDto findById(Long id);

    void deleteById(Long id);
}
