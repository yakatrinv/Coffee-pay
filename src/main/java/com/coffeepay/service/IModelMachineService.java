package com.coffeepay.service;

import com.coffeepay.dto.ModelMachineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IModelMachineService {
    Page<ModelMachineDto> findAllPage(String model, String brand, Pageable pageable);

    List<ModelMachineDto> getAllModels();

    void save(ModelMachineDto modelMachineDto);

    void update(ModelMachineDto modelMachineDto);

    ModelMachineDto findById(Long id);

    void deleteById(Long id);
}
