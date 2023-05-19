package com.coffeepay.conrtroller;

import com.coffeepay.dto.ModelMachineDto;
import com.coffeepay.service.IModelMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataViews.ADD_AFTER_MODELS_MACHINE_PAGE;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_MODELS_MACHINE_LIST;
import static util.DataViews.ATTR_MODEL_MACHINE;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_SEARCH_MODEL_MACHINE_BRAND;
import static util.DataViews.ATTR_SEARCH_MODEL_MACHINE_MODEL;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_MODEL_MACHINE;
import static util.DataViews.PAGE_EDIT_MODEL_MACHINE;
import static util.DataViews.PAGE_LIST_MODELS_MACHINE;
import static util.DataViews.PAGE_REDIRECT_LIST_MODELS_MACHINE;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_MODELS_MACHINE_PAGE)
public class ModelMachineController {
    private final IModelMachineService modelMachineService;

    @GetMapping
    public String getModelMachine(Model model,
                                  @RequestParam(value = ATTR_SEARCH_MODEL_MACHINE_MODEL, defaultValue = "") String modelMachine,
                                  @RequestParam(value = ATTR_SEARCH_MODEL_MACHINE_BRAND, defaultValue = "") String brand,
                                  @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ModelMachineDto> pageable = modelMachineService.findAllPage(brand, modelMachine, pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_MODELS_MACHINE_PAGE);
        model.addAttribute(ATTR_MODELS_MACHINE_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_MODEL_MACHINE_MODEL, modelMachine);
        model.addAttribute(ATTR_SEARCH_MODEL_MACHINE_BRAND, brand);

        return PAGE_LIST_MODELS_MACHINE;
    }

    @GetMapping(URL_NEW)
    public String newModelMachine(Model model) {
        model.addAttribute(ATTR_MODEL_MACHINE, new ModelMachineDto());
        return PAGE_ADD_MODEL_MACHINE;
    }

    @PostMapping
    public String createModelMachine(@ModelAttribute(ATTR_MODEL_MACHINE) @Valid ModelMachineDto modelMachineDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return PAGE_ADD_MODEL_MACHINE;
        }
        modelMachineService.save(modelMachineDto);
        return PAGE_REDIRECT_LIST_MODELS_MACHINE;
    }

    @GetMapping(URL_EDIT)
    public String editModelMachine(Model model,
                                   @PathVariable(ATTR_ID) long id) {
        model.addAttribute(ATTR_MODEL_MACHINE, modelMachineService.findById(id));
        return PAGE_EDIT_MODEL_MACHINE;
    }

    @PatchMapping(URL_UPDATE)
    public String updateModelMachine(@ModelAttribute(ATTR_MODEL_MACHINE) @Valid ModelMachineDto modelMachineDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_MODEL_MACHINE;
        }
        modelMachineService.save(modelMachineDto);
        return PAGE_REDIRECT_LIST_MODELS_MACHINE;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteModelMachine(@PathVariable(ATTR_ID) long id) {
        modelMachineService.deleteById(id);
        return PAGE_REDIRECT_LIST_MODELS_MACHINE;
    }

}
