package com.coffeepay.service.impl;

import com.coffeepay.dto.ModelMachineDto;
import com.coffeepay.model.ModelMachine;
import com.coffeepay.repository.ModelMachineRepository;
import com.coffeepay.service.IModelMachineService;
import com.coffeepay.specification.ModelMachineSpecification;
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

import static util.DataGeneral.MODEL_MACHINE_CLASS;
import static util.DataGeneral.MODEL_MACHINE_DTO_CLASS;

@Service
@Transactional
@RequiredArgsConstructor
public class ModelMachineService implements IModelMachineService {
    private final ModelMachineRepository modelMachineRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ModelMachineDto> findAllPage(String brand, String model, Pageable pageable) {
        Specification<ModelMachine> likeBrandAndModel = Specification
                .where(ModelMachineSpecification.likeBrand(brand))
                .and(ModelMachineSpecification.likeModel(model));

        Page<ModelMachine> modelMachinePage = modelMachineRepository.findAll(likeBrandAndModel, pageable);

        return new PageImpl<>(
                modelMachinePage
                        .stream()
                        .map(modelMachine -> modelMapper.map(modelMachine, MODEL_MACHINE_DTO_CLASS))
                        .toList(),
                pageable,
                modelMachinePage.getTotalElements());
    }

    @Override
    public List<ModelMachineDto> getAllModels() {
        return modelMachineRepository.findAll()
                .stream()
                .map(modelMachine -> modelMapper.map(modelMachine, MODEL_MACHINE_DTO_CLASS))
                .toList();
    }

    @Override
    public void save(ModelMachineDto modelMachineDto) {
        modelMachineRepository.save(modelMapper.map(modelMachineDto, MODEL_MACHINE_CLASS));
    }

    @Override
    public void update(ModelMachineDto modelMachineDto) {
        modelMachineRepository.save(modelMapper.map(modelMachineDto, MODEL_MACHINE_CLASS));
    }

    @Override
    public ModelMachineDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(modelMachineRepository::findById)
                .map(modelMachine -> modelMapper.map(modelMachine, MODEL_MACHINE_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        modelMachineRepository.deleteById(id);
    }
}
